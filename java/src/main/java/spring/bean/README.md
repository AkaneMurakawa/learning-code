## Spring Bean

### BeanDefinition元信息
| 属性 (Property)          | 说明                                                    |
| ------------------------ | ------------------------------------------------------- |
| Class                    | Bean全类名，必须是具体类，不能用抽象类接口 (无法初始化)    |
| Name                     | Bean的名称或ID， Bean的识别符                           |
| Scope                    | Bean的作用域 （如: singleton、 prototype 等）           |
| Constructor arguments    | Bean构造器参数 (用于依赖注入)                           |
| Properties               | Bean属性设置 (用于依赖注入)                             |
| Auto-wiring mode         | Bean自动绑定模式 (如: 通过名称 byName，通过类型byType)   |
| Lazy initialization mode | Bean延迟初始化模式 (延迟和非延迟)                        |
| initialization method    | Bean初始化回调方法名称                                  |
| Destruction method       | Bean销毁回调方法名称                                    |

### BeanDefinition 构建
* 通过BeanDefinitionBuilder
* 通过AbstractBeanDefinition以及派生类

### BeanNameGenerator
* AnnotationBeanNameGenerator
* DefaultBeanNameGenerator
