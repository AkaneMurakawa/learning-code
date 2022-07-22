## 一、设置cookie和删除
```python
@app.route("/index")
def index():
    """
        一、设置cookie和删除cookie
        通过make_response返回response
        def set_cookie(
            self,
            key,
            value="",
            max_age=None,
            expires=None,
            path="/",
            domain=None,
            secure=False,
            httponly=False,
            samesite=None,
        ):
    :return:
    """
    resp = make_response("set_cookie_test")
    resp.set_cookie("lang", "zh")
    # resp.delete_cookie("lang")
    return resp
```

## 二、获取cookie
```python
@app.route("/getCookie")
def getCookie():
    """
        二、获取cookie
    :return:
    """
    return request.cookies.get("lang")
```