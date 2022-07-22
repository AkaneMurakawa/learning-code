## 一、跳转到指定的界面
```python
@app.route("/index")
def index():
    data = {
        "language": "python"
    }
    return render_template("index.html", **data)
```

## 二、Jinja2语法
```html
过滤器，语法
<p>{{'test' | 函数}}</p>

字符串过滤器
<p>{{'test' | trim}}</p>
禁止转义，容易xss攻击
<p>{{'test' | safe}}</p>

<p>{{'test' | first}}</p>
```

## 三、自定义过滤器
方法一
```python
# 第一步：
def list_step_2(li):
    """
        自定义过滤器
    :param li:
    :return:
    """
    return li[::2]

# 第二步：
# 注册过滤器
app.add_template_filter(list_step_2)
```

方法二
```python
@app.add_template_filter
def list_step_3(li):
    """
        自定义过滤器
    :param li:
    :return:
    """
    return li[::3]
```

## 四、控制语句
```html
{% if %} {% endif %}

{% for item in samples %}{% endfor %}
```

## 五、宏
```html
{% macro input() %}
    <input type="text" value="">
{% endmacro %}

使用
{{input()}}

外部引入宏
{% import "macro_input.html" as m_input %}
使用
{% m_input.input() %}
```

## 六、flash