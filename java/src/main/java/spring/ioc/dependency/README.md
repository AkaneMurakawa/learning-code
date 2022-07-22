## Spring IoC 依赖查找和依赖注入

### 示例
[lookup](lookup/DependencyLookupDemo.java)

[injection](injection/DependencyInjectionDemo.java)


### ApplicationContext 和 BeanFactory
```text
ConfigurableApplicationContext <- ApplicationContext <- BeanFactory
```
BeanFactory 是 Spring 底层 IoC 容器

ApplicationContext是BeanFactory的子接口，但是其提供了更多了特性。


### BeanFactory 和 FactoryBean
答:

BeanFactory 是 IoC 底层容器

FactoryBean 是创 Bean 的一种方式，帮助实现复杂的初始化逻辑

