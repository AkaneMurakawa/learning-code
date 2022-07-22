package spring.ioc.dependency.lookup;


import spring.ioc.dependency.annotation.Super;
import spring.ioc.dependency.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * 依赖查找
 */
public class DependencyLookupDemo {

    public static void main(String[] args) {
        // 右键dependency-lookup-context.xml，选择Copy Relative Path(Ctrl+Alt+Shift+C)，这也是我们常用的拷贝方式
        // 点击则可以link到文件上

        // 1. 配置XML文件
        // 2. 启动Spring Context
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup-context.xml");
        lookupRealTime(beanFactory);
        lookupInLazy(beanFactory);
        /**
         *  No qualifying spring.bean of type 'spring.ioc.dependency.domain.User' available: expected single matching spring.bean but found 2: user,superUser
         *  配置primary="true">
         */
        lookupByType(beanFactory);
        lookupByCollecionType(beanFactory);
        lookupByAnnotation(beanFactory);

    }

    /**
     * 根据注解查找
     *
     * @param beanFactory
     */
    private static void lookupByAnnotation(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory)beanFactory;
            Map<String, User> users = (Map)listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("注解查找:" + users);
        }
    }

    /**
     * 根据集合类型查找
     *
     * @param beanFactory
     */
    private static void lookupByCollecionType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory)beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("集合类型查找:" + users);
        }
    }

    /**
     * 根据单个类型查找
     * 泛型
     * @param beanFactory
     */
    private static void lookupByType(BeanFactory beanFactory){
        User user = (User)beanFactory.getBean(User.class);
        System.out.println("单个类型查找:" + user.toString());
    }

    /**
     * 实时查找
     * @param beanFactory
     */
    private static void lookupRealTime(BeanFactory beanFactory){
        User user = (User)beanFactory.getBean("user");
        System.out.println("实时查找:" + user.toString());
    }

    /**
     * 延迟查找
     * @param beanFactory
     */
    private static void lookupInLazy(BeanFactory beanFactory){
        ObjectFactory<User> objectFactory = (ObjectFactory)beanFactory.getBean("objectFactory");
        User user = objectFactory.getObject();
        System.out.println("延迟查找:" + user.toString());
    }
}
