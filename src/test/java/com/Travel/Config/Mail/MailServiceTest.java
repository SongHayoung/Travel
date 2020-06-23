package com.Travel.Config.Mail;

import com.Travel.Config.Database.MyBatis.MyBatisConfig;
import com.Travel.biz.Mail.Service.MailService;
import com.Travel.init.RootConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {RootConfig.class, MailConfig.class, MyBatisConfig.class})
public class MailServiceTest {
    @Autowired MailService mailService;

    @Test
    public void sendMail() {
        mailService.sendRegisterMail("hello2234@naver.com");
        mailService.sendMail("hello2234@naver.com","테스트","test");
    }

}
