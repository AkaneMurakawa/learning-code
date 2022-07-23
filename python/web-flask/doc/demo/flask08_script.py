#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from flask import Flask
from flask_script import Manager


app = Flask(__name__)
# 启动命令管理
manager = Manager(app)


@app.route("/index")
def index():
    # current_app.config
    # g.username = "test"
    print("index")


if __name__ == '__main__':
    print('----------------------------------------------------------------------------------------')
    print('Python Web Flask Demo')
    print('----------------------------------------------------------------------------------------')
    manager.run()
