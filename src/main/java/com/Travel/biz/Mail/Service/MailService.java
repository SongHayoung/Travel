package com.Travel.biz.Mail.Service;

import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public interface MailService {
    MimeMessagePreparator plainMailSend(String to, String subject, String content);
    MimeMessagePreparator authMailSend(String to);
}
