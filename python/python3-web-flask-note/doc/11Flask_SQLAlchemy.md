## 一、SQLAlchemy
```python
# 配置连接参数
class Config(object):
    SQLALCHEMY_DATABASE_URI = "mysql://root:kaguya@127.0.0.1:3306/test"
    SQLALCHEMY_TRACK_MODIFICATIONS = True


# 创建数据库sqlalchemy工具对象
app.config.from_object(Config)
db = SQLAlchemy(app)

```

