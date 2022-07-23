package spring.bean;


import spring.ioc.dependency.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bean 别名
 */
public class BeanAliasDemo {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-definition-context.xml");
        lookup(beanFactory);
    }

    /**
     * 通过别名获取 Bean
     *
     * @param beanFactory
     */
    private static void lookup(BeanFactory beanFactory) {
        User user1 = beanFactory.getBean("user", User.class);
        User user2 = beanFactory.getBean("akane-user", User.class);
        System.out.println(user1.toString());
        System.out.println(user2.toString());
        System.out.println("user 和 akane-user是否相同:" + (user1 == user2));
    }

}
