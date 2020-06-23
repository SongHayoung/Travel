package com.Travel.biz.Mail.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@PropertySource("/properties/mail.properties")
public class MailServiceImpl implements MailService{
    @Value("${mail.username}") String admin;
    @Value("${mail.regMailSubject}") String regSubject;

    public MimeMessagePreparator plainMailSend(String to, String subject, String content) {
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override public void prepare(MimeMessage mimeMessage) throws Exception {
                final MimeMessageHelper helper = setMailMessage(mimeMessage, to, subject);
                helper.setText(content, true);
            }
        };
        return preparator;
    }

    public MimeMessagePreparator authMailSend(String to) {
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override public void prepare(MimeMessage mimeMessage) throws Exception {
                final MimeMessageHelper helper = setMailMessage(mimeMessage, to, regSubject);
                helper.setText("가입메일", true);
            }
        };
        return preparator;
    }

    private MimeMessageHelper setMailMessage(MimeMessage mimeMessage, String to, String subject) throws MessagingException {
        final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom(admin);
        helper.setTo(to);
        helper.setSubject(subject);

        return helper;
    }
}
