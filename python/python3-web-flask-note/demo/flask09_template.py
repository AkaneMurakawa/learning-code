#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from flask import Flask, render_template

app = Flask(__name__)


@app.route("/index")
def index():
    data = {
        "language": "python"
    }
    return render_template("index.html", **data)

def list_step_2(li):
    """
        自定义过滤器
    :param li:
    :return:
    """
    return li[::2]


# 注册过滤器
app.add_template_filter(list_step_2)

@app.add_template_filter
def list_step_3(li):
    """
        自定义过滤器
    :param li:
    :return:
    """
    return li[::3]

if __name__ == '__main__':
    print('----------------------------------------------------------------------------------------')
    print('Python Web Flask Demo')
    print('----------------------------------------------------------------------------------------')
    app.run(host="127.0.0.1", port=8080, debug=True)
