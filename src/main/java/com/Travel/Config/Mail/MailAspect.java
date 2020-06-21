package com.Travel.Config.Mail;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

@Aspect
public class MailAspect {
    @Autowired JavaMailSender mailSender;

    @Around("execution(* com.Travel.biz.Mail..*.*MailSend(..))")
    public void around(ProceedingJoinPoint pjp) {
        MimeMessagePreparator preparator = null;
        try {
            preparator = (MimeMessagePreparator) pjp.proceed();
            mailSender.send(preparator);
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
