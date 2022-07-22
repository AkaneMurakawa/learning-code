package mybatis.config;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import java.util.Collection;
import java.util.Properties;

/**
 * 覆盖对象工厂的默认行为
 */
public class ExampleObjectFactory extends DefaultObjectFactory {

    public Object create(Class type) {
        return super.create(type);
    }

    public void setProperties(Properties properties) {
        super.setProperties(properties);
    }

    public <T> boolean isCollection(Class<T> type) {
        return Collection.class.isAssignableFrom(type);
    }
}
