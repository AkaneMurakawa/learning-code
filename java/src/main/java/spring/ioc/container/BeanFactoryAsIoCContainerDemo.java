package spring.ioc.container;

import spring.ioc.dependency.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * BeanFactory 作为 IoC 容器
 */
public class BeanFactoryAsIoCContainerDemo {

    public static void main(String[] args) {
        // 1、创建 BeanFactory 容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // XML 配置 ClassPath 路径
        String location = "classpath:/META-INF/dependency-lookup-context.xml";
        // 3、加载配置
        int beanDefinitionsCount = reader.loadBeanDefinitions(location);
        System.out.println("Bean 定义加载的数量:" + beanDefinitionsCount);

        lookupByCollecionType(beanFactory);
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
}
