package spring.bean;

import spring.ioc.dependency.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * BeanDefinition 构建
 */
public class BeanDefinitionCreationDemo {

    public static void main(String[] args) {
        // 方式一、通过BeanDefinitionBuilder
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder
                .addPropertyValue("name", "Akane Murakawa")
                .addPropertyValue("bio", "spring bean");
        // BeanDefinition 并非 Bean 终态，可以自定义修改
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        System.out.println(beanDefinition);

        // 方式二、通过GenericBeanDefinition
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        // 设置Bean类型
        genericBeanDefinition.setBeanClass(User.class);
        // 通过MutablePropertyValues 批量操作属性
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues
                .add("name", "Akane Murakawa")
                .add("bio", "spring bean");
        // 和方法一beanDefinitionBuilder.addPropertyValue其实是类似的
        genericBeanDefinition.setPropertyValues(propertyValues);

        BeanDefinition originatingBeanDefinition = genericBeanDefinition.getOriginatingBeanDefinition();
        System.out.println(originatingBeanDefinition);
    }
}
