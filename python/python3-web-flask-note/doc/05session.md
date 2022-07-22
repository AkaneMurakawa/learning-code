## 一、设置session
```python
@app.route("/setSession")
def setSession():
    """
        一、设置cookie
    :return:
    """
    session["language"] = "zh"
    session["username"] = "python"
    return "session set"
```

## 二、获取session
```python
#第一步：
# Flask中session需要用到的密钥

#第二步:
@app.route("/getSession")
def getSession():
    """
        二、获取cookie
    :return:
    """
    return session.get("username")
```