package com.Travel.Init;

import com.Travel.Init.Core.CoreConfiguration;
import com.Travel.Init.FeedService.FeedServiceContextConfiguration;
import com.Travel.Init.UserService.UserServiceContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@Import({CoreConfiguration.class, UserServiceContextConfiguration.class, FeedServiceContextConfiguration.class})
public class RootContextConfiguration {
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
