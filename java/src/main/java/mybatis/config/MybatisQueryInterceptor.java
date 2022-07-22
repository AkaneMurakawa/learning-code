package mybatis.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * mybatis 查询拦截器
 */
@Slf4j
@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class MybatisQueryInterceptor implements Interceptor {

    /**
     * 拦截的 COMMAND 类型
     */
    private static final Set<String> INTERCEPTOR_COMMAND = new HashSet<String>() {{
        add("SELECT");
    }};

    /**
     * 方法白名单
     */
    private static final Set<String> WHITE_LIST = new HashSet<String>() {{
        add("mybatis.mapper.UserMapper.selectOne");
    }};

    /**
     * 跳过给 sql 添加 limit
     * 添加跳过类型时，一定要填 大写 的
     */
    private static final Set<String> SKIP_LIMIT_COMMAND = new HashSet<String>() {{
        add("FOR UPDATE");
        add("INSERT INTO");
        add("GROUP BY");
        add("COUNT");
    }};

    /**
     * 跳过给 sql 跳过监控
     * 添加跳过类型时，一定要填 大写 的
     */
    private static final Set<String> SKIP_MONITOR_COMMAND = new HashSet<String>() {{
        add("INSERT INTO");
        add("GROUP BY");
        add("COUNT");
        add("WHERE");
        add("HAVING");
        add("LIMIT");
    }};

    private final static String LIMIT = "LIMIT";

    private final static String SEMICOLON = ";";

    /**
     * 拦截时执行的操作
     *
     * @param invocation { 代理对象，被监控方法对象，当前被监控方法运行时需要的实参 }
     * @return
     * @throws Throwable
     */
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler handler = (StatementHandler) invocation.getTarget();

        if (handler instanceof RoutingStatementHandler) {
            handler = (BaseStatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
        }

        MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(handler, "mappedStatement");
        //获取sql类型，转化为小写
        String commandType = mappedStatement.getSqlCommandType().toString().toUpperCase();
        //获取方法路径
        String id = mappedStatement.getId();

        //只拦截select类型的查询和过滤白名单
        if (!INTERCEPTOR_COMMAND.contains(commandType) || WHITE_LIST.contains(id)) {
            return invocation.proceed();
        }
        //获取sql
        String originSql = handler.getBoundSql().getSql();

        //将SQL转化为大写来校验
        String originSqlCheck = originSql.toUpperCase();

        //跳过有筛选条件的SQL
        for (String str : SKIP_MONITOR_COMMAND) {
            if (originSqlCheck.contains(str)) {
                return invocation.proceed();
            }
        }

        //校验是否有需要末尾追加 limit
        boolean flag = true;
        for (String str : SKIP_LIMIT_COMMAND) {
            if (originSqlCheck.contains(str)) {
                flag = false;
                break;
            }
        }

        //查询后面添加limit限制最大条数
        if (flag && !originSqlCheck.contains(LIMIT)) {
            //处理查询语句末尾有分号的 SQL
            if (originSql.substring(originSql.length() - 1).equals(SEMICOLON)) {
                originSql = originSql.substring(0, originSql.length() - 1);
            }
            originSql = originSql + " \n limit 20000";
            //通过反射修改sql语句
            Field field = handler.getBoundSql().getClass().getDeclaredField("sql");
            field.setAccessible(true);
            field.set(handler.getBoundSql(), originSql);
        }

        return invocation.proceed();
    }

    /**
     * 拦截器用于封装目标对象的
     *
     * @param target
     * @return
     */
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 用于在 Mybatis 配置文件中指定一些属性的。
     *
     * @param properties
     */
    public void setProperties(Properties properties) {

    }

    /**
     * 反射工具类
     */
    static class ReflectUtil {
        public ReflectUtil() {
        }

        /**
         * 改变 Accessible,便于访问private等属性
         *
         * @param field
         */
        private static void makeAccessible(Field field) {
            if (!Modifier.isPublic(field.getModifiers())) {
                field.setAccessible(true);
            }
        }

        /**
         * 获取 object 的字段,字段名称为filedName,获取不到返回null
         *
         * @param object
         * @param filedName
         * @return
         */
        private static Field getDeclaredField(Object object, String filedName) {
            Class superClass = object.getClass();

            while (superClass != Object.class) {
                try {
                    return superClass.getDeclaredField(filedName);
                } catch (NoSuchFieldException var4) {
                    superClass = superClass.getSuperclass();
                }
            }
            return null;
        }

        /**
         * 获取object字段fieldName的值,如果字段不存在直接抛异常
         *
         * @param object
         * @param fieldName
         * @return
         */
        public static Object getFieldValue(Object object, String fieldName) {
            Field field = getDeclaredField(object, fieldName);
            if (field == null) {
                throw new IllegalArgumentException("");
            } else {
                makeAccessible(field);
                Object result = null;

                try {
                    result = field.get(object);
                } catch (IllegalAccessException var5) {
                    var5.printStackTrace();
                }
                return result;
            }
        }
    }
}
