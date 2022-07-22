package mybatis;

import mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;

public class ApplicationDemo {

    /**
     * SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例。
     * 使用 SqlSessionFactory 的最佳实践是在应用运行期间不要重复创建多次，多次重建 SqlSessionFactory 被视为一种代码“坏习惯”。
     * 因此 SqlSessionFactory 的最佳作用域是应用作用域。 有很多方法可以做到，最简单的就是使用单例模式或者静态单例模式。
     */
    SqlSessionFactory sqlSessionFactory;

    public static void main(String[] args)throws Exception {
        ApplicationDemo demo = new ApplicationDemo();
//        demo.sqlSessionMapper();
        demo.cacheTest();
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
     * 二级缓存测试
     * @throws IOException
     */
    public void cacheTest()throws IOException{
        // Mybatis的Resources工具类
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        // 获取SqlSession工厂
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();

        System.out.println(sqlSession1.getMapper(UserMapper.class).selectOneCache(6001));
//        sqlSession1.close();
        sqlSession1.commit();
        System.out.println(sqlSession2.getMapper(UserMapper.class).selectOneCache(6001));
    }


}
