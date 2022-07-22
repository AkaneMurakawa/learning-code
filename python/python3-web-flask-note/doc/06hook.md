## 一、请求handle —— hook
```python
app.route("/index")
def index():
    print("index")


@app.before_request
def before_request():
    print("每次之前")


@app.before_first_request
def before_first_request():
    print("第一次之前")


@app.after_request
def after_request():
    print("每次请求后，非异常状态下")


@app.teardown_request
def before_first_request():
    print("每次请求后，无论异常都执行")
```
