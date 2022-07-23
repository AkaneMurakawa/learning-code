#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from flask import Flask, request


app = Flask(__name__)


@app.route('/')
def index():
    """
        request
    """
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
    print("name: %s" % name)
    print("host: %s" % host)
    print("url: %s" % url)
    print("method: %s" % method)
    print("args: %s" % args)
    print("data: %s" % data)
    print("cookies: %s" % cookies)
    print("headers: %s" % headers)
    print("headers: %s" % headers)
    """
        结果
        name: AkaneMurakawa
        host: localhost:8080
        url: http://localhost:8080/?name=AkaneMurakawa
        method: GET
        args: ImmutableMultiDict([('name', 'AkaneMurakawa')])
        data: b''
        cookies: ImmutableMultiDict([])
        headers: User-Agent: PostmanRuntime/7.24.1
        Accept: */*
        Cache-Control: no-cache
        Postman-Token: b8970e6c-e03d-49cc-81a6-1b85f0fe4350
        Host: localhost:8080
        Accept-Encoding: gzip, deflate, br
        Connection: keep-alive
        Content-Type: multipart/form-data; boundary=--------------------------637029444884811782838285
        Content-Length: 172
    """
    return 'request test'


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


if __name__ == '__main__':
    print('----------------------------------------------------------------------------------------')
    print('Python Web Flask Demo')
    print('----------------------------------------------------------------------------------------')
    app.run(host="127.0.0.1", port=8080, debug=True)
