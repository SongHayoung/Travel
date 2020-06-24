package com.Travel.Config.Mail;

import com.Travel.init.ContextMyBatis;
import com.Travel.biz.Mail.Service.MailService;
import com.Travel.init.ContextMail;
import com.Travel.init.RootContextConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {RootContextConfiguration.class, ContextMail.class, ContextMyBatis.class})
public class MailServiceTest {
    @Autowired MailService mailService;

    @Test
    public void sendMail() {
        mailService.sendRegisterMail("hello2234@naver.com");
        mailService.sendMail("hello2234@naver.com","테스트","test");
    }
}
