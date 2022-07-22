#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from flask import Flask

app = Flask(__name__)


@app.route("/index")
def index():
    # current_app.config
    # g.username = "test"
    print("index")


if __name__ == '__main__':
    print('----------------------------------------------------------------------------------------')
    print('Python Web Flask Demo')
    print('----------------------------------------------------------------------------------------')
    app.run(host="127.0.0.1", port=8080, debug=True)
