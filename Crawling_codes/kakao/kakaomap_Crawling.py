#!/usr/bin/env python
# coding: utf-8

# In[32]:


from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from bs4 import BeautifulSoup
import time

url = "https://map.kakao.com/"
search_term = "교촌치킨"

driver = webdriver.Chrome()
driver.get(url)

# Search for the term
search_box = driver.find_element(By.XPATH, "//input[@id='search.keyword.query']")
search_box.send_keys(search_term)
search_box.send_keys(Keys.ENTER)
time.sleep(5)

# Extract '상세보기' links
soup = BeautifulSoup(driver.page_source, 'html.parser')
detail_links = [link.get('href') for link in soup.find_all('a', {'data-id': 'moreview'})]

# Iterate through each detail link
for detail_url in detail_links:
    try:
        # Navigate to the detailed page
        driver.execute_script("window.open(arguments[0]);", detail_url)
        driver.switch_to.window(driver.window_handles[1])
        time.sleep(3)

        # Extract details
        detailed_soup = BeautifulSoup(driver.page_source, 'html.parser')
        name = detailed_soup.select_one("h2.tit_location").text.strip()
        address = detailed_soup.select_one("span.txt_address").text.strip()
        rating = detailed_soup.select_one("em.num_rate").text.strip().split('점')[0]

        print(f"지점 이름: {name}, 위치: {address}, 평점: {rating}")

        # Extract user reviews
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
            
            print(f'User Nickname: {user_nickname}')
            print(f'Review Date: {review_date}')
            print(f'Review Content: {review_content}')
            print(f'User Rating: {user_rating}\n')

        # Close the current tab and switch back
        driver.close()
        driver.switch_to.window(driver.window_handles[0])
    except Exception as e:
        print(f"Error fetching details: {e}")
        continue

driver.quit()


# In[ ]:




