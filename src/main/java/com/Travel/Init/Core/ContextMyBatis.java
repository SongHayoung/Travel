package com.Travel.Init.Core;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
public class ContextMyBatis {
    @Autowired ApplicationContext applicationContext;
    private static final String CLASS_PATH = "configurations/Database/Mybatis/";
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource((DataSource) applicationContext.getBean("dataSource"));
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(CLASS_PATH + "config/mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(new ClassPathResource[]{
                new ClassPathResource(CLASS_PATH + "mapper/user-mapper.xml"),
                new ClassPathResource(CLASS_PATH + "mapper/area-mapper.xml"),
                new ClassPathResource(CLASS_PATH + "mapper/feed-mapper.xml"),
                new ClassPathResource(CLASS_PATH + "mapper/plan-mapper.xml"),
                new ClassPathResource(CLASS_PATH + "mapper/dailyPlan-mapper.xml"),
                new ClassPathResource(CLASS_PATH + "mapper/feedAreas-mapper.xml"),
        });

        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSession sqlSession() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sqlSessionTemplate;
    }
}
