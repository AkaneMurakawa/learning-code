#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
内容：
1、初始化Flask
2、运行Flask
3、Flask请求路由
4、转换器
"""

"""
导入包，flask的核心依赖
"""
from flask import Flask, redirect, url_for

from util.regex_converter import RegexConverter

"""
    1、初始化Flask
    :param __name__ 当前模块的名字，如果启动是当前模块，则是写死的，为'__main__'，如果作为其他的模块引用，则是其他模块的名字
    2、flask以这个模块所在的目录为总目录，默认这个目录下的static为静态目录，template为模板目录
    
    3、Flask的构造函数
    def __init__(
        self,
        import_name,
        static_url_path=None,
        static_folder="static",
        static_host=None,
        host_matching=False,
        subdomain_matching=False,
        template_folder="templates",
        instance_path=None,
        instance_relative_config=False,
        root_path=None,
    ):
"""
app = Flask(__name__)
# 将自定义的转换器添加到flask，使用：@app.route('/detail/<re(r'[0-9]{10}'):sku_id>')
app.url_map.converters["re"] = RegexConverter


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


if __name__ == '__main__':
    print('----------------------------------------------------------------------------------------')
    print('Python Web Flask Demo')
    print('----------------------------------------------------------------------------------------')
    # 查看flask中路由信息
    print(app.url_map)
    print('----------------------------------------------------------------------------------------')
    """
        可以自行执行其他参数
        run(self, host=None, port=None, debug=None, load_dotenv=True, **options):
        
        app.run(host="0.0.0.0", port=8080) 说明: 0.0.0.0通配符，任何地址都可以访问
    """
    app.run(host="127.0.0.1", port=8080, debug=True)
