package spring.bean;

import spring.ioc.dependency.domain.User;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bean 实例化
 */
public class BeanInstantinationDemo {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-instantiation-context.xml");

        // 1. 静态方法实例化 Bean
        User bean = beanFactory.getBean("user-by-static-method", User.class);
        // 2. 实例 (Bean) 方法 实例化 Bean
        User bean1 = beanFactory.getBean("user-by-instance-method", User.class);
        // 3. FactoryBean 实例化 Bean
        User bean2 = beanFactory.getBean("user-by-factory-bean", User.class);
        System.out.println(bean == bean1);
        System.out.println(bean1 == bean2);

    }
}
