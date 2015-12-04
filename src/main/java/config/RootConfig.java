package config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 루트 설정용 클래스.
 * 이 클래스는 스프링의 root-context.xml 의 역할을 대신한다.
 *
 */
@Configuration
@ComponentScan(basePackages="org.ranestar", 
						excludeFilters = {@Filter(type = FilterType.ANNOTATION, value = Controller.class),
	                            				@Filter(type = FilterType.ANNOTATION, value = ControllerAdvice.class) })
public class RootConfig {

	@Value("${jdbc.driverClassName}")
    private String jdbcDriverClassName;
    
    @Value("${jdbc.url}") 
    private String jdbcUrl;
    
    @Value("${jdbc.username}")
    private String jdbcUsername;
    
    @Value("${jdbc.password}")
    private String jdbcPassword;

    /*
     * 프로퍼티 홀더는 다른 빈들이 사용하는 프로퍼티들을 로딩하기 때문에, static 메소드로 실행된다.
     * 다른 일반 빈들이 만들어지기전에 먼저 만들어져야 한다.
     * @return
     */
    @Bean
    public static PropertyPlaceholderConfigurer properties() {
      PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();

      Resource[] resources =
          new ClassPathResource[] {new ClassPathResource("configuration/db.properties"), 
        		  							   new ClassPathResource("configuration/path.properties")};
      ppc.setLocations(resources);
      ppc.setIgnoreResourceNotFound(false);
      ppc.setIgnoreUnresolvablePlaceholders(false);

      return ppc;
    }
    
    
    @Bean(destroyMethod = "close")
    public DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(this.jdbcDriverClassName);
        dataSource.setUrl(this.jdbcUrl);
        dataSource.setUsername(this.jdbcUsername);
        dataSource.setPassword(this.jdbcPassword);
        dataSource.setValidationQuery("select 1");
        return dataSource;
    }
    
    @Bean
    public DataSourceInitializer dataSourceInitializer() throws Exception{
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScripts(new PathMatchingResourcePatternResolver().getResources("classpath*:db/*.sql"));
        //databasePopulator.addScript(new ClassPathResource("db/*.sql"));
        databasePopulator.setIgnoreFailedDrops(true);

        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(this.dataSource());
        initializer.setDatabasePopulator(databasePopulator);

        return initializer;
    }
	
}
