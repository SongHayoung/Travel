package com.Travel.Config.Mail;

import com.Travel.Config.Database.MyBatis.MyBatisConfig;
import com.Travel.biz.Mail.Service.MailService;
import com.Travel.init.RootConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@PropertySource("/properties/mail.properties")
@ContextConfiguration(classes= {RootConfig.class, MailConfig.class, MyBatisConfig.class})
public class MailServiceTest {
    @Autowired MailService mailService;

    @Value("${mail.username}") String senderName;

    @Test
    public void sendMail() {
        mailService.plainMailSend(senderName,"마지막 테스트 메일입니다","테스트 메일입니다");
    }
}
