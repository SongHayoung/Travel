package com.Travel.Core.MailService;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
    void sendMail(String to, String subject, String content);
}
