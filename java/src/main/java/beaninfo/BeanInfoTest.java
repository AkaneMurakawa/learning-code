package beaninfo;

import base.User;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * 查看BeanInfo 示例
 */
public class BeanInfoTest {

    public static void main(String[] args) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(User.class);

        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            System.out.println("Name: " + pd.getName());
            System.out.println("writeMethod: " + pd.getWriteMethod());
            System.out.println("ReadMethod: " + pd.getReadMethod());
        }
    }
}
