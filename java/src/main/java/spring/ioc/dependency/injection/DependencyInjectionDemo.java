package spring.ioc.dependency.injection;

import spring.ioc.dependency.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;
import spring.ioc.dependency.repository.UserRepository;

/**
 * 依赖注入
 */
public class DependencyInjectionDemo {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");
        dependencySource(beanFactory);
        dependencyInjectionAndDependencyLookup(beanFactory);
        beanFactoryAndApplicationContext(beanFactory);
    }

    /**
     * beanFactory 和 ApplicationContext
     * 结论：
     * 1、BeanFactory 是 Spring 底层 IoC 容器
     * 2、ApplicationContext是BeanFactory的子接口，但是其提供了更多了特性。
     *
     * @param beanFactory
     */
    private static void beanFactoryAndApplicationContext(BeanFactory beanFactory) {
        UserRepository userRepository = beanFactory.getBean("userRepository", UserRepository.class);

        // 依赖注入
        // org.springframework.beans.factory.support.DefaultListableBeanFactory
        System.out.println("容器内建 Bean对象(依赖):" + userRepository.getBeanFactory());
        // 依赖注入
        ObjectFactory<ApplicationContext> objectFactory = userRepository.getObjectFactory();
        // org.springframework.context.support.ClassPathXmlApplicationContext
        // 说明注入的这个ApplicationContext就是我们当前的BeanFactory
        System.out.println("ObjectFactory: " + objectFactory.getObject() + "\n");


        // false
        System.out.println("BeanFactory 和 ApplicationContext: " + (userRepository.getBeanFactory() == beanFactory));
        // true
        System.out.println("BeanFactory 和 ApplicationContext: " + (userRepository.getObjectFactory().getObject() == beanFactory));
    }


    /**
     * 依赖查找 和 依赖注入
     *
     * @param beanFactory
     */
    private static void dependencyInjectionAndDependencyLookup(BeanFactory beanFactory) {
        UserRepository userRepository = beanFactory.getBean("userRepository", UserRepository.class);
        BeanFactory beanFactory1 = userRepository.getBeanFactory();
        System.out.println(beanFactory1);

        // 依赖查找 (错误) NoSuchBeanDefinitionException
        // 这里是依赖查找，但是上面的BeanFactory是我们依赖注入的。这里报NoSuchBeanDefinitionException，说明依赖查找 和 依赖注入并不是同一个东西
        // System.out.println(beanFactory.getBean(BeanFactory.class));
    }

    /**
     * 依赖来源
     *
     * @param beanFactory
     */
    private static void dependencySource(BeanFactory beanFactory) {
        // 依赖来源一: 自定义 Bean
        // spring.ioc.dependency.repository.UserRepository
        UserRepository userRepository = beanFactory.getBean("userRepository", UserRepository.class);
        System.out.println("依赖来源一 自定义 Bean:" + userRepository);

        // 依赖来源二: 容器内建 Bean对象(依赖)
        // 依赖注入
        // org.springframework.beans.factory.support.DefaultListableBeanFactory
        System.out.println("依赖来源二 容器内建 Bean对象(依赖):" + userRepository.getBeanFactory());

        // 依赖来源三: 容器内建Bean
        Environment environment = beanFactory.getBean(Environment.class);
        System.out.println("依赖来源三 容器内建Bean:" + environment);
    }
}
