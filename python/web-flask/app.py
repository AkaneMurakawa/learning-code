#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
    Note Online System
"""
from flask import Flask, render_template, jsonify, request, send_from_directory
from flask_sqlalchemy import SQLAlchemy


class Config(object):
    """
        配置连接参数
    """
    SQLALCHEMY_DATABASE_URI = "mysql://root:kaguya@127.0.0.1:3306/test"
    SQLALCHEMY_TRACK_MODIFICATIONS = True


app = Flask(__name__)
# 设置jinja2语法格式{[]}，解决和vue的冲突
app.jinja_env.variable_start_string = '{['
app.jinja_env.variable_end_string = ']}'
app.config.from_object(Config)
# 创建数据库SQLAlchemy工具对象
db = SQLAlchemy(app)


class User(db.Model):
    """用户表模型"""
    __tablename__ = "user"

    # 默认自增
    id = db.Column(db.Integer, primary_key=True)
    user_id = db.Column(db.Integer, unique=True)
    username = db.Column(db.String(64))
    desc = db.Column(db.String(255))


class Note(db.Model):
    """笔记表模型"""
    __tablename__ = "note"

    id = db.Column(db.Integer, primary_key=True)
    user_id = db.Column(db.Integer)
    title = db.Column(db.String(100))
    content = db.Column(db.Text)
    # 2020-04-30 13:56:24序列化有问题
    create_time = db.Column(db.String(19))

    def __init__(self, title, content, create_time):
        super().__init__()
        self.title = title
        self.content = content
        self.create_time = create_time


@app.route('/favicon.ico')
def favicon():
    """设置favicon"""
    return send_from_directory('static', 'favicon.ico', mimetype='image/vnd.microsoft.icon')


@app.route('/')
def index():
    """跳转到首页"""
    return render_template("index.html")


@app.route('/note/index')
def note_index():
    """跳转到笔记首页"""
    return render_template("note_index.html")


@app.route('/note/list', methods=["GET"])
def note_list():
    """获取笔记列表，分页是不可能分页了"""
    data = Note.query.all()
    data_list = []
    for note in data:
        # 将对象转换为dict，并截取content
        temp = {
            "id": note.id,
            "user_id": note.user_id,
            "title": note.title,
            "content": note.content[:100],
            "create_time": note.create_time
        }
        data_list.append(temp)
    # jsonify对json的封装，将dict转换为json，但不能将obj转换为json，坑
    return jsonify(data_list)


@app.route('/note/add/')
def note_add():
    """Note添加"""
    note = Note(request.args["title"], request.args["content"], request.args["create_time"])
    # 没有实现多表关联，太麻烦了
    note.user_id = 60001
    db.session.add(note)
    db.session.commit()
    db.session.close()
    return jsonify({
        "result": 1,
        "message": "success"
    })

@app.route('/note/detail/<int:id>')
def note_detail(id):
    """Note详情"""
    data = Note.query.filter_by(id=id).first()
    if data:
        note = Note(data.title, data.content, data.create_time)
        return render_template("note_detail.html", note=note)
    else:
        return render_template("index.html")


@app.route('/note/delete', methods=["POST"])
def note_delete():
    """删除Note"""
    id = request.form.get("id")
    result = Note.query.filter(Note.id == id).delete()
    db.session.commit()
    db.session.close()
    if result:
        return jsonify({
            "result": 1,
            "message": "success"
        })
    else:
        return jsonify({
            "result": 0,
            "message": "fail"
        })


if __name__ == '__main__':
    print('----------------------------------------------------------------------------------------')
    print('Python Web Flask Demo')
    print('----------------------------------------------------------------------------------------')
    app.run(host="127.0.0.1", port=8080, debug=True)
