package config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@EnableTransactionManagement
public class MybatisConfig {
	
	@Autowired
	DataSource dataSource;
	
	@Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("org.ranestar.model");
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:sql/mybatis/mapper/**/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }
    
    @Bean
    public SqlSession sqlSession() throws Exception{
        SqlSession sqlSession = new SqlSessionTemplate(this.sqlSessionFactory());
        return sqlSession;
    }
    
    @Bean
    public DataSourceTransactionManager transactionManager(){
    	DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    	transactionManager.setDataSource(dataSource);
    	return transactionManager;
    }
    
    @Bean
    public TransactionTemplate transactionTemplate(){
    	TransactionTemplate transactionTemplate = new TransactionTemplate();
    	transactionTemplate.setTransactionManager(this.transactionManager());
    	return transactionTemplate;
    }

}
