## 一、request
```python
    name = request.form.get("name")
    host = request.host
    url = request.url
    method = request.method
    # query string请求查询字符串
    args = request.args
    data = request.data
    cookies = request.cookies
    headers = request.headers
    files = request.files
```

## 二、文件上传
```python
@app.route("/upload", methods=["POST"])
def upload():
    file_obj = request.files.get("pic")
    if file_obj is None:
        return "file not found"

    # f = open("./demo.png", "wb")
    # data = file_obj.read()
    # f.write(data)
    # f.close()
    # 等价于
    file_obj.save("./demo.png")
    return "upload success"
```