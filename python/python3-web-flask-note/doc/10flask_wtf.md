## 一、wtf
```python
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

```

