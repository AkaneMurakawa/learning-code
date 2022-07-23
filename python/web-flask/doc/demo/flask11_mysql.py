#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from flask import Flask
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)

# 配置连接参数
class Config(object):
    SQLALCHEMY_DATABASE_URI = "mysql://root:kaguya@127.0.0.1:3306/test"
    SQLALCHEMY_TRACK_MODIFICATIONS = True


# 创建数据库sqlalchemy工具对象
app.config.from_object(Config)
db = SQLAlchemy(app)


# 数据库模型类型，用于db.create_all()
class User(db.Model):
    # 数据库的表名
    __tablename__ = "user"
    # 默认自增
    id = db.Column(db.INT, primary_key=True)
    username = db.Column(db.String, unique=True)
    desc = db.Column(db.String)


@app.route("/index")
def index():
    return "index"


# --------------------------------------------------------------------------------------------------------------------------------------------------------
# CRUD
def create():
    user1 = User(username="user1", desc="yui")
    user2 = User(username="user2", desc="poi")
    db.session.add(user1)
    db.session.add(user2)
    db.session.add_all([user1, user2])
    db.session.commit()


def select():
    db.session.query(User).all()
    db.session.query(User).get(id=1)
    db.session.commit()




if __name__ == '__main__':
    print('----------------------------------------------------------------------------------------')
    print('Python Web Flask Demo')
    print('----------------------------------------------------------------------------------------')
    # db.drop_all()
    # db.create_all()

    # app.run(host="127.0.0.1", port=8080, debug=True)
