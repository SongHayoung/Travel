package com.Travel.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Driver;

@Configuration
@Import(ContextMyBatis.class)
@PropertySource("/properties/datasource.properties")
public class ContextDatabase {
    @Value("${db.driverClass}") Class<? extends Driver> driver;
    @Value("${db.username}") String user;
    @Value("${db.password}") String password;
    @Value("${db.url}") String url;

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource source = new SimpleDriverDataSource();
        source.setDriverClass(driver);
        source.setUrl(url);
        source.setUsername(user);
        source.setPassword(password);

        return source;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(dataSource());
        return tm;
    }
}
