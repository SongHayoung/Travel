package com.Travel.Init.Core;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/properties/dataSource.properties")
@Import(ContextMyBatis.class)
public class ContextDatabase {
    @Value("${datasource.driverClass}")String driver;
    @Value("${datasource.username}") String user;
    @Value("${datasource.password}") String password;
    @Value("${datasource.url}") String url;
    @Value("${datasource.initialSize}") int initialSize;
    @Value("${datasource.maxTotal}") int maxTotal;
    @Value("${datasource.maxIdle}") int maxIdle;
    @Value("${datasource.minIdle}") int minIdle;
    @Value("${datasource.maxWaitMillis}") int maxWaitMillis;
    @Value("${datasource.testOnBorrow}") boolean testOnBorrow;
    @Value("${datasource.testWhileIdle}") boolean testWhileIdle;
    @Value("${datasource.validationQuery}") String validationQuery;
    @Value("${datasource.minEvictableIdleTimeMillis}") int minEvictableIdleTimeMillis;
    @Value("${datasource.timeBetweenEvictionRunsMillis}") int timeBetweenEvictionRunsMillis;
    @Value("${datasource.numTestsPerEvictionRun}") int numTestsPerEvictionRun;
    @Value("${datasource.poolPreparedStatements}") boolean poolPreparedStatements;
    @Value("${datasource.maxOpenPreparedStatements}") int maxOpenPreparedStatements;

    @Bean
    public DataSource dataSource() {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName(driver);
        source.setUrl(url);
        source.setUsername(user);
        source.setPassword(password);
        //BasicDataSource 클래스 생성 후 최초로 getConnection() 메스드를 호출 시 커넥션 풀에 넣을 커넥션 수
        source.setInitialSize(initialSize);
        //동시에 사용할 수 있는 최대 커넥션 수
        source.setMaxTotal(maxTotal);
        //커넥션 풀에 반납할 때 최대로 유지될 수 있는 커넥션 수
        source.setMaxIdle(maxIdle);
        //최소한으로 유지할 커넥션 수
        source.setMinIdle(minIdle);
        //Pool 고갈시 최대 대기 시간
        source.setMaxWaitMillis(maxWaitMillis);
        //유효성 검사
        source.setTestOnBorrow(testOnBorrow);
        source.setTestWhileIdle(testWhileIdle);
        //DBMS 설정 MYSQL
        source.setValidationQuery(validationQuery);
        //Connection이 없을 시 커넥션 끊기
        source.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        source.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        source.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        //statement pool
        source.setPoolPreparedStatements(poolPreparedStatements);
        //최대 기억할 pool개수 커넥션 개수 * 풀 개수 * sql길이 만큼 캐시에 저장된다
        source.setMaxOpenPreparedStatements(maxOpenPreparedStatements);

        return source;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(dataSource());
        return tm;
    }
}
