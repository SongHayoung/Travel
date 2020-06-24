package com.Travel.init;

import com.Travel.Config.Mail.MailAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class ContextAspectJ {
    @Bean
    public MailAspect mailAspect() {
        MailAspect mailAspect = new MailAspect();
        return mailAspect;
    }
}
