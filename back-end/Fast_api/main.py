from fastapi import FastAPI

app = FastAPI()

@app.get("/")
def index():
    return "hi"

@app.get("/show")
def show():
    return {'data':1234}