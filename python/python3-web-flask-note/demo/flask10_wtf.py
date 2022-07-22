#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from flask import Flask, render_template
from flask_wtf import FlaskForm
from wtforms import StringField
from wtforms.validators import DataRequired

app = Flask(__name__)


class RegisterForm(FlaskForm):
    """
        自定义注册表单模型类
    """
    user_name = StringField(label=u"用户名", validators=[DataRequired])
    password = StringField(label=u"密码", validators=[DataRequired(u'密码不能为空')])


@app.route("/index")
def index():
    form = RegisterForm()
    if form.validate_on_submit():
        # 获取数据
        user_name = form.user_name.data
        password = form.password.data
    return render_template("register.html", form=form)



if __name__ == '__main__':
    print('----------------------------------------------------------------------------------------')
    print('Python Web Flask Demo')
    print('----------------------------------------------------------------------------------------')
    app.run(host="127.0.0.1", port=8080, debug=True)
