#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from werkzeug.routing import BaseConverter


class RegexConverter(BaseConverter):
    """
        自定义转换器
    """
    def __init__(self, map, regex):
        """
        @app.route('/detail/<re(r'[0-9]{10}'):sku_id>')
        :param map:
        :param regex: 自定义的属性
        """
        # 调用父类
        super(RegexConverter, self).__init__(map)
        self.regex = regex

    def to_python(self, value):
        super().to_python(value)

    def to_url(self, value):
        super().to_url(value)
