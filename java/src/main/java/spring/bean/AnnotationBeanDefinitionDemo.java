package spring.bean;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import spring.ioc.dependency.domain.User;

/**
 * 注册 BeanDefinition
 */
// 3. 通过@Import 来进行导入
@Import(AnnotationBeanDefinitionDemo.Config.class)
public class AnnotationBeanDefinitionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class (配置类)
//        applicationContext.register(Config.class);
        applicationContext.register(AnnotationBeanDefinitionDemo.class);
        // 启动引用上下文
        applicationContext.refresh();

        // 通过 BeanDefinition 注册API 实现
        // 1. 命名 Bean 的注册方式
        registerBeanDefinition(applicationContext, "akane");
        // 2. 非命名 Bean 的注册方式
        registerBeanDefinition(applicationContext);

        System.out.println("Config Beans:" + applicationContext.getBeansOfType(Config.class));
        System.out.println("User Beans:" + applicationContext.getBeansOfType(User.class));

        // 显示地关闭 Spring 应用上下文
        applicationContext.close();

    }

    /**
     * Bean 的注册方式
     *
     * @param registry
     * @param beanName
     */
    public static void registerBeanDefinition(BeanDefinitionRegistry registry, String beanName) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("name", "Akane").addPropertyValue("bio", "bio");

        // 判断如果 BeanName 参数存在时
        if (StringUtils.hasText(beanName)) {
            // 命名方式
            registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        } else {
            // 非命名方式
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(), registry);
        }
    }

    public static void registerBeanDefinition(BeanDefinitionRegistry registry) {
        registerBeanDefinition(registry, null);
    }

    // 2. 通过@Component方式
    // 定义当前类作为 Spring Bean (组件)
    @Component
    public static class Config {
        // 1. 通过@Bean 方式定义

        /**
         * 通过注解的方式
         *
         * @return
         */
        @Bean(name = {"user", "akane-user"})
        public User user() {
            User user = new User();
            user.setName("Annotation");
            user.setBio("desc");
            return user;
        }
    }

}
