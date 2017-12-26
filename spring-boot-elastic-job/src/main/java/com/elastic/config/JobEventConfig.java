package com.elastic.config;

import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobEventConfig {

    @Bean
    public JobEventConfiguration jobEventConfiguration(@Value("${jobEventConfig.url}") final String url, @Value("${jobEventConfig.driverClassName}") final String driverClassName,
                                                       @Value("${jobEventConfig.username}") final String username, @Value("${jobEventConfig.password}") final String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return new JobEventRdbConfiguration(dataSource);
    }
}