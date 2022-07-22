package mybatis;

import mybatis.mapper.UserMapper;
import mybatis.model.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;

public class SqlSessionFactoryDemo {

    /**
     * SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例。
     * 使用 SqlSessionFactory 的最佳实践是在应用运行期间不要重复创建多次，多次重建 SqlSessionFactory 被视为一种代码“坏习惯”。
     * 因此 SqlSessionFactory 的最佳作用域是应用作用域。 有很多方法可以做到，最简单的就是使用单例模式或者静态单例模式。
     */
    SqlSessionFactory sqlSessionFactory;

    public static void main(String[] args)throws Exception {
        SqlSessionFactoryDemo demo = new SqlSessionFactoryDemo();
//        demo.sqlSessionScope();
        demo.sqlSessionEnviroment();
    }

    /**
     * Configuration -> SqlSessionFactory -> SqlSession -> Mapper -> excutor -> JDBC
     * @throws IOException
     */
    public void sqlSession()throws IOException{
        // Mybatis的Resources工具类
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");

        /**
         * SqlSessionFactoryBuilder
         * 这个类可以被实例化、使用和丢弃，一旦创建了 SqlSessionFactory，就不再需要它了。
         * 因此 SqlSessionFactoryBuilder 实例的最佳作用域是方法作用域（也就是局部方法变量）。
         * 你可以重用 SqlSessionFactoryBuilder 来创建多个 SqlSessionFactory 实例，
         * 但最好还是不要一直保留着它，以保证所有的 XML 解析资源可以被释放给更重要的事情。
         */
        // 获取SqlSession工厂
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User o = (User)sqlSession.selectOne("mybatis.mybatis.mapper.UserMapper.selectOne", 6001);
        System.out.println(o.toString());
    }

    /**
     * sqlSession.getMapper测试
     * @throws IOException
     */
    public void sqlSessionMapper()throws IOException{
        // Mybatis的Resources工具类
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        // 获取SqlSession工厂
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(mapper.selectOne(6001));
    }

    /**
     * sqlSessionScope作用域
     * @throws IOException
     */
    public void sqlSessionScope()throws IOException{
        // Mybatis的Resources工具类
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        // 获取SqlSession工厂
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        /**
         * 每个线程都应该有它自己的 SqlSession 实例。
         * SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。
         */
        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            System.out.println(mapper.selectOne(6001));
        }

    }

    public void sqlSessionEnviroment()throws IOException{
        // Mybatis的Resources工具类
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        // 获取SqlSession工厂，设置environment
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, "development");

        try(SqlSession sqlSession = sqlSessionFactory.openSession()){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            System.out.println(mapper.selectOne(6001));
        }
    }

    private String selectPersonSql() {
        return new SQL(){{
            SELECT("P.ID, P.USERNAME, P.PASSWORD, P.FULL_NAME");
            SELECT("P.LAST_NAME, P.CREATED_ON, P.UPDATED_ON");
            FROM("PERSON P");
            FROM("ACCOUNT A");
            INNER_JOIN("DEPARTMENT D on D.ID = P.DEPARTMENT_ID");
            INNER_JOIN("COMPANY C on D.COMPANY_ID = C.ID");
            WHERE("P.ID = A.ID");
            WHERE("P.FIRST_NAME like ?");
            OR();
            WHERE("P.LAST_NAME like ?");
            GROUP_BY("P.ID");
            HAVING("P.LAST_NAME like ?");
            OR();
            HAVING("P.FIRST_NAME like ?");
            ORDER_BY("P.ID");
            ORDER_BY("P.FULL_NAME");
        }}.toString();
    }
}
