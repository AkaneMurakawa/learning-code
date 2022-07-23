#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from flask import Flask, session

app = Flask(__name__)
# Flask中session需要用到的密钥
app.config["SECRET_KEY"] = "RSAFDSA123"

@app.route("/setSession")
def setSession():
    """
        一、设置cookie
    :return:
    """
    session["language"] = "zh"
    session["username"] = "python"
    return "session set"


@app.route("/getSession")
def getSession():
    """
        二、获取cookie
    :return:
    """
    return session.get("username")

if __name__ == '__main__':
    print('----------------------------------------------------------------------------------------')
    print('Python Web Flask Demo')
    print('----------------------------------------------------------------------------------------')
    app.run(host="127.0.0.1", port=8080, debug=True)
