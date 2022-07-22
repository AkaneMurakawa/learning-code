package mybatis.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.defaults.DefaultSqlSession.StrictMap;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

/**
 * mybatis 执行sql前拦截器
 */
@Slf4j
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class PrepareInterceptor implements Interceptor {

    /**
     * Insert方法白名单
     */
    private static final Set<String> WHITE_LIST = new HashSet<String>() {{
        add("mybatis.mapper.UserMapper.selectOne");
    }};

    @SuppressWarnings("unchecked")
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        //注解中method的值
        String methodName = invocation.getMethod().getName();
        //sql类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if ("update".equals(methodName)) {
            Object object = invocation.getArgs()[1];
            //插入sql中指定字段，主键tid设置为全球唯一字段
            if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                //批量插入
                if(object instanceof StrictMap) {
                    StrictMap<Object> objs = (StrictMap<Object>)object;
                    Object collection = objs.get("collection");
                    if(collection instanceof Collection) {
                        Collection<Object> c = (Collection<Object>)collection;
                        Iterator<Object> it = c.iterator();
                        while (it.hasNext()) {
                            Object o = it.next();
                            reflectTid(o);
                        }
                    }
                }else {
                    //单条插入

                    //获取方法路径
                    String id = mappedStatement.getId();
                    if (!WHITE_LIST.contains(id)){
                        reflectTid(object);
                    }
                }
            } else if (SqlCommandType.UPDATE.equals(sqlCommandType)) {}
        }
        return invocation.proceed();
    }
    /**
     * 反射注入id
     * @param object
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void reflectTid(Object object) throws NoSuchFieldException, IllegalAccessException {
        Field fieldId = object.getClass().getDeclaredField("id");
        fieldId.setAccessible(true);
        long id = UUID.randomUUID().node();
        fieldId.set(object, id);
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
    }
}
