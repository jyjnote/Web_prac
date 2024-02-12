#!/usr/bin/env python
# coding: utf-8

# In[1]:


from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from bs4 import BeautifulSoup
import time
import pandas as pd

url = "https://map.kakao.com/"
search_term = "교촌치킨"

driver = webdriver.Chrome()
driver.get(url)

search_box = driver.find_element(By.XPATH, "//input[@id='search.keyword.query']")
search_box.send_keys(search_term)
search_box.send_keys(Keys.ENTER)
time.sleep(5)

try:
    more_button = driver.find_element(By.ID, "info.search.place.more")
    driver.execute_script("arguments[0].click();", more_button)
    time.sleep(3)
except Exception as e:
    print(f"Error clicking more button: {e}")

detail_links = []

for page_num in range(1, 4):
    try:
        page_button = driver.find_element(By.ID, f"info.search.page.no{page_num}")
        driver.execute_script("arguments[0].click();", page_button)
        time.sleep(3)
        soup = BeautifulSoup(driver.page_source, 'html.parser')
        detail_links.extend([link.get('href') for link in soup.find_all('a', {'data-id': 'moreview'})])
    except Exception as e:
        print(f"Error on page {page_num}: {e}")

detail_links = list(set(detail_links))

# Data storage
all_reviews = []

for detail_url in detail_links:
    try:
        driver.execute_script("window.open(arguments[0]);", detail_url)
        driver.switch_to.window(driver.window_handles[-1])
        time.sleep(3)

        # Click '후기 더보기' button multiple times using class name
        while True:
            try:
                more_reviews_button = driver.find_element(By.CLASS_NAME, "txt_more")
                more_reviews_button.click()
                time.sleep(1)
            except:
                break

        detailed_soup = BeautifulSoup(driver.page_source, 'html.parser')
        name = detailed_soup.select_one("h2.tit_location").text.strip()
        address = detailed_soup.select_one("span.txt_address").text.strip()
        rating = detailed_soup.select_one("em.num_rate").text.strip().split('점')[0]

        review_items = detailed_soup.find_all('li', {'data-id': True})
        for item in review_items:
            user_nickname = item.find('a', class_='link_user').text.strip()
            review_date = item.find('span', class_='time_write').text.strip()
            review_content = item.find('p', class_='txt_comment').text.strip()
            star_element = item.find('span', class_='ico_star inner_star')
            star_width = star_element.get('style')
            user_rating = 0
            if star_width:
                percentage = int(star_width.split('width:')[1].replace('%;', '').strip())
                user_rating = percentage / 20

            review_data = {
                '지점명': name,
                '위치': address,
                '평균평점': rating,
                '유저 아이디': user_nickname,
                '유저가 준 점수': user_rating,
                '후기 글': review_content
            }
            all_reviews.append(review_data)

        driver.close()
        driver.switch_to.window(driver.window_handles[0])
    except Exception as e:
        print(f"Error fetching details: {e}")

driver.quit()

# Create DataFrame
df = pd.DataFrame(all_reviews)
df

