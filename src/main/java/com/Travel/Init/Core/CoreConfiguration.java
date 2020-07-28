package com.Travel.Init.Core;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;

@Import({ContextDatabase.class, ContextMail.class, ContextMessage.class, ContextSecurity.class, ContextSecurity.class, ContextSwagger.class})
@ContextConfiguration(locations = {"/configurations/log4j/log4j-config.xml"})
@ComponentScan(basePackages = {"com.Travel.Core.*"})
public class CoreConfiguration {
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties(){
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application-core.yml"));
        propertySourcesPlaceholderConfigurer.setProperties(yaml.getObject());
        return propertySourcesPlaceholderConfigurer;
    }
}
