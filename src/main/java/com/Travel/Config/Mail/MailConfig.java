package com.Travel.Config.Mail;

import com.Travel.biz.Mail.Service.MailService;
import com.Travel.biz.Mail.Service.MailServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@Import(value = MailAspectConfig.class)
@PropertySource("/properties/mail.properties")
public class MailConfig {
    @Value("${mail.host}") String host;
    @Value("${mail.port}") String port;
    @Value("${mail.username}") String username;
    @Value("${mail.password}") String password;
    @Value("${mail.defaultEncoding}") String defaultEncoding;
    @Value("${mail.protocol}") String protocol;

    private static final String MAIL_DEBUG = "mail.smtp.debug";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    private static final String MAIL_SMTP_SSL_TRUST = "mail.smtp.ssl.trust";
    private static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_PORT = "mail.smtp.port";
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String MAIL_SOCKETFACTORY_PORT = "mail.smtp.socketFactory.port";
    private static final String MAIL_SOCKETFACTORY_CLASS = "mail.smtp.socketFactory.class";

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setDefaultEncoding(defaultEncoding);
        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty(MAIL_TRANSPORT_PROTOCOL, protocol);
        properties.setProperty(MAIL_SMTP_STARTTLS_ENABLE, "true");
        properties.setProperty(MAIL_SMTP_SSL_TRUST, host);
        properties.setProperty(MAIL_SMTP_HOST, host);
        properties.setProperty(MAIL_SMTP_AUTH, "true");
        properties.setProperty(MAIL_SMTP_PORT, port);
        properties.setProperty(MAIL_SOCKETFACTORY_PORT, port);
        properties.setProperty(MAIL_SOCKETFACTORY_CLASS, "javax.net.ssl.SSLSocketFactory");
        properties.setProperty(MAIL_DEBUG,"true");
        return properties;
    }

    @Bean
    public MailService mailService() {
        MailServiceImpl mailService = new MailServiceImpl();
        return mailService;
    }
}
