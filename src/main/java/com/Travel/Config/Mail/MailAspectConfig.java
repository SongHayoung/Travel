package com.Travel.Config.Mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class MailAspectConfig {
    @Bean
    public MailAspect mailAspect() {
        MailAspect mailAspect = new MailAspect();
        return mailAspect;
    }
}
