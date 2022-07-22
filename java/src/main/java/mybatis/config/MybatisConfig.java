package mybatis.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置类，相当于我们的spring-config.xml
 */
@Configuration
@ComponentScan(basePackages = {"mybatis.*"}) // component组件扫描
@MapperScan(basePackages = {"mybatis.*"}) // mapper扫描
public class MybatisConfig {

    private static String driver;
    private static String url;
    private static String userName;
    private static String password;

    /**
     * 获取属性
     */
    static {
        Properties properties = new Properties();
        try {
            InputStream inputStream = Object.class.getResourceAsStream("/jdbc.properties");
            properties.load(inputStream);
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            userName = properties.getProperty("username");
            password = properties.getProperty("password");

        }catch (IOException e){}
    }

    /**
     * 注入sqlSession
     * 在基础的 MyBatis 用法中，是通过 SqlSessionFactoryBuilder 来创建 SqlSessionFactory 的。
     * 而在 MyBatis-Spring 中，则使用 SqlSessionFactoryBean 来创建。
     *
     * 这意味着由 Spring 最终创建的 bean 并不是 SqlSessionFactoryBean 本身，
     * 而是工厂类（SqlSessionFactoryBean）的 getObject() 方法的返回结果。
     * 这种情况下，Spring 将会在应用启动时为你创建 SqlSessionFactory，
     * 并使用 sqlSessionFactory 这个名字存储起来。
     *
     * 需要注意的是SqlSessionFactoryBean implements FactoryBean<SqlSessionFactory>
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        // factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:META-INF/mybatis/mapper/**/*Mapper.xml"));
        return factoryBean.getObject();

    }

    /**
     * 注入连接
     * @return
     */
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
//        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    /**
     * Mybatis加入Spring事务
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    /**
     * 使用 SqlSession
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionTemplate sqlSession() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }

    /**
     * 自动注入，一般我们不会采用这种方式，而是采用MapperScan扫描的方式
     * @return
     * @throws Exception
     */
//    @Bean
//    public UserMapper userMapper() throws Exception {
//        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
//        return sqlSessionTemplate.getMapper(UserMapper.class);
//    }
}
