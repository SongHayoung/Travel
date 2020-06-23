package com.Travel.biz.Mail.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@PropertySource("/properties/mail.properties")
public class MailServiceImpl implements MailService{
    @Value("${mail.username}") String admin;
    @Value("${mail.regMailSubject}") String regSubject;
    @Autowired JavaMailSender mailSender;

    public MimeMessagePreparator postMailToUser(String to, String subject, String content) {
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override public void prepare(MimeMessage mimeMessage) throws Exception {
                final MimeMessageHelper helper = setMailHeader(mimeMessage, to, subject);
                helper.setText(content, true);
            }
        };
        return preparator;
    }

    public void sendMail(String to, String subject, String content) {
        mailSender.send(postMailToUser(to,subject,content));
    }

    public void sendRegisterMail(String to) {
        sendMail(to, regSubject, "가입메일");
    }

    private MimeMessageHelper setMailHeader(MimeMessage mimeMessage, String to, String subject) throws MessagingException {
        final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom(admin);
        helper.setTo(to);
        helper.setSubject(subject);

        return helper;
    }
}