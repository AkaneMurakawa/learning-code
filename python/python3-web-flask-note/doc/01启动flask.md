
## 一、启动程序入口 —— Main
```python
# 第一步：初始化Flask
app = Flask(__name__)

# 第二步：运行Flask
"""
    可以自行执行其他参数
    run(self, host=None, port=None, debug=None, load_dotenv=True, **options):
    
    app.run(host="0.0.0.0", port=8080) 说明: 0.0.0.0通配符，任何地址都可以访问
"""
app.run(host="127.0.0.1", port=8080)
```

## 二、请求路由
* 返回json格式
* 请求转发
* 请求重定向

```python
@app.route('/')
def index():
    """
        一、请求路由
    :return: json
    """
    return 'Hello World!'


@app.route('/listPage', methods=['GET'])
def list_page():
    """
        二、请求路由
        methods指定请求方式
    :return: json
    """
    return 'list page!'


@app.route('/redirect', methods=['GET'])
def login():
    """
        三、请求路由
        redirect
    """
    # 通过视图函数url_for，找到对应的url路径，参数：为对应的函数名称
    url = url_for("list_page")
    return redirect(url)


@app.route('/detail/<int:sku_id>')
def detail(sku_id):
    """
         路径转换器
        @app.route('/detail/<int:sku_id>')
        @app.route('/detail/<sku_id>') # 不加类型转换器，默认是普通字符串规则
    :return:
    """
    return "test %s" % sku_id
```

## 三、转换器
1.自定义转换器`util.regex_converter.RegexConverter`  
2.将自定义的转换器添加到flask
```python
app.url_map.converters["re"] = RegexConverter
```
3.使用
```python
@app.route('/detail/<re(r'[0-9]{10}'):sku_id>')
```