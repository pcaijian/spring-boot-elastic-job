package com.elastic;

import java.io.IOException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.bind.annotation.InitBinder;

import com.alibaba.druid.pool.DruidDataSource;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

@SpringBootApplication
public class SpringBootElasticJobApplication {
	@Bean
    @InitBinder
    public DataSource dataSource() {
      // 引用阿里连接池
      DruidDataSource ds = new DruidDataSource();
      ds.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=gbk&zeroDateTimeBehavior=convertToNull");
      ds.setUsername("root");
      ds.setPassword("root");
      ds.setInitialSize(2);
      ds.setMinIdle(1);
      ds.setMaxActive(20);
      ds.setTimeBetweenEvictionRunsMillis(60000);
      ds.setDriverClassName("com.mysql.jdbc.Driver");
      ds.setMaxWait(60000);
      ds.setMinEvictableIdleTimeMillis(300000);
      ds.setTestOnBorrow(false);
      ds.setTestOnReturn(false);
      ds.setTestWhileIdle(true);
      ds.setValidationQuery("SELECT 'x'");
      ds.setPoolPreparedStatements(false);
      return ds;
    }
	/**
     * spring事务管理
     * @return
     */
    @Bean(name = "txManager")
    public DataSourceTransactionManager dataSourceTransactionManager() {
      DataSourceTransactionManager txManager = new DataSourceTransactionManager();
      txManager.setDataSource(dataSource());
      return txManager;
    }
    

    /**
     * 数据库session的连接工厂配置 
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
      SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
      sqlSessionFactoryBean.setDataSource(dataSource());
      // 扫描mybatis的配置文件
      sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("/config/mybatis/mybatis-config.xml"));
      // 扫描mapper
      Resource[] mapperResources = new PathMatchingResourcePatternResolver().getResources("classpath*:/config/mybatis/mapper/*.xml");
      sqlSessionFactoryBean.setMapperLocations(mapperResources);
      return sqlSessionFactoryBean;
    }
	
    @Bean
    public MapperScannerConfigurer MapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.elastic.mapper");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        return mapperScannerConfigurer;
    }
    public static void main(String[] args) {
        SpringApplication.run(SpringBootElasticJobApplication.class, args);
    }
}
