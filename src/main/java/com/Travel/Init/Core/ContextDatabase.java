package com.Travel.Init.Core;

import org.apache.commons.dbcp2.BasicDataSource;
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
@PropertySource("classpath:/properties/dataSource.properties")
@Import(ContextMyBatis.class)
public class ContextDatabase {
    @Value("${datasource.driverClass}")Class<? extends Driver> driver;
    @Value("${datasource.driverClass}")String ddriver;
    @Value("${datasource.username}") String user;
    @Value("${datasource.password}") String password;
    @Value("${datasource.url}") String url;

    @Bean
    public DataSource dataSource() {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName(ddriver);
        source.setUrl(url);
        source.setUsername(user);
        source.setPassword(password);
        //BasicDataSource 클래스 생성 후 최초로 getConnection() 메스드를 호출 시 커넥션 풀에 넣을 커넥션 수
        source.setInitialSize(20);
        //동시에 사용할 수 있는 최대 커넥션 수
        source.setMaxTotal(20);
        //커넥션 풀에 반납할 때 최대로 유지될 수 있는 커넥션 수
        source.setMaxIdle(20);
        //최소한으로 유지할 커넥션 수
        source.setMinIdle(0);
        //Pool 고갈시 최대 대기 시간
        source.setMaxWaitMillis(3000);
        //유효성 검사 false
        source.setTestOnBorrow(false);
        //DBMS 설정 MYSQL
        source.setValidationQuery("SELECT 1");
        //Connection이 없을 시 커넥션 끊
        source.setMinEvictableIdleTimeMillis(-1);
        //statement pool
        source.setPoolPreparedStatements(true);
        //최대 기억할 pool개수 커넥션 개수 * 풀 개수 * sql길이 만큼 캐시에 저장된다
        source.setMaxOpenPreparedStatements(50);

        return source;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(dataSource());
        return tm;
    }
}
