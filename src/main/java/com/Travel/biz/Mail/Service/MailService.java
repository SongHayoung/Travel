package com.Travel.biz.Mail.Service;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
    void sendMail(String to, String subject, String content);
    void sendRegisterMail(String to);
}
