package mybatis.config;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import java.util.Properties;

@Intercepts({@Signature(
        type= Executor.class,
        method = "update",
        args = {MappedStatement.class,Object.class})})
public class ExamplePlugin implements Interceptor {

    @SuppressWarnings("unchecked")
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
        // 获得方法类型，update、insert
        String methodName = invocation.getMethod().getName();
        System.out.println("methodName: " + methodName);
        // 获得sql类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        System.out.println("sqlType SELECT:" + SqlCommandType.SELECT.equals(sqlCommandType));

        // implement pre processing if need
        // 执行
        Object returnObject = invocation.proceed();
        // implement post processing if need
        return returnObject;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {

    }
}
