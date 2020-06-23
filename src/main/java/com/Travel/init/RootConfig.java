package com.Travel.init;

import com.Travel.biz.User.Dao.UserDao;
import com.Travel.biz.User.Dao.UserDaoJdbc;
import com.Travel.biz.User.Service.Info.UserInfoService;
import com.Travel.biz.User.Service.Info.UserInfoServiceImpl;
import com.Travel.biz.User.Service.Register.UserRegisterService;
import com.Travel.biz.User.Service.Register.UserRegisterServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Driver;

@Configuration
@PropertySource("/properties/datasource.properties")
public class RootConfig {
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

    @Bean
    public UserDao userDao() {
        UserDaoJdbc userDao = new UserDaoJdbc();
        return userDao;
    }

    @Bean
    public UserInfoService userInfoService() {
        UserInfoServiceImpl userInfoService = new UserInfoServiceImpl();
        return userInfoService;
    }

    @Bean
    public UserRegisterService userRegisterService() {
        UserRegisterServiceImpl userRegisterService = new UserRegisterServiceImpl();
        return userRegisterService;
    }
}
