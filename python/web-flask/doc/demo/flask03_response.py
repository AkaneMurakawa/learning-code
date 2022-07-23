#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import json

from flask import Flask, abort, Response, make_response, jsonify

app = Flask(__name__)


@app.route("/abort", methods=["GET"])
def abort_test():
    """
        abort: 放弃
        abort()可以立即终止函数的执行，并可以返回前端特定的信息
        例如：
            1.返回指定状态码，必须是http标准存在的
            abort(400)
            2.指定响应体信息
            resp = Response("upload failed")
            abort(resp)
    :return:
    """
    # abort(400)

    resp = Response("upload failed")
    abort(resp)
    return "upload success"


@app.route("/index")
def index():
     # 返回自定义的响应信息 元组：响应体——状态码——响应头
    # return ("index", 400, [("name", "python")])
    # 返回字典
    # return ("index", 400, {"name": "python"})
     # 自定义返回状态码Status Code
    return ("index", "233 mystatus", {"name": "python"})


@app.route("/resp")
def resp():
    """
        通过make_response返回response
    :return:
    """
    resp = make_response("test")
    resp.status= "233 mystatus"
    resp.headers["code"] = "python"
    return resp


@app.route("/json")
def json():
    """
        json就是字符串
        json.dumps 将python转为字符串
        json.loads 将字符串转为python
    :return:
    """
    data = {
        "language": "python",
        "age": 18
    }
    str = json.dumps(data)
    return str, 200, {"Content-Type": "applicaiton/json"}


@app.route("/jsonify")
def jsonify_test():
    """
        jsonify
        :return:
    """
    data = {
        "language": "python",
        "age": 20
    }
    return jsonify(data)
    # return jsonify(language="python", age=20)


if __name__ == '__main__':
    print('----------------------------------------------------------------------------------------')
    print('Python Web Flask Demo')
    print('----------------------------------------------------------------------------------------')
    app.run(host="127.0.0.1", port=8080, debug=True)
