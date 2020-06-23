package com.Travel.biz.User;

import com.Travel.Config.Database.MyBatis.MyBatisConfig;
import com.Travel.Config.Mail.MailConfig;
import com.Travel.biz.User.Dao.UserDao;
import com.Travel.biz.User.Service.Register.DuplicateUserEmailException;
import com.Travel.biz.User.Service.Register.DuplicateUserIdException;
import com.Travel.biz.User.Service.Register.InvalidEmailAddressException;
import com.Travel.biz.User.Service.Register.UserRegisterService;
import com.Travel.biz.User.VO.UserVO;
import com.Travel.init.RootConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@Rollback
@Transactional(propagation= Propagation.REQUIRES_NEW)
@ContextConfiguration(classes= {RootConfig.class, MyBatisConfig.class, MailConfig.class})
public class UserRegisterServiceTest {
    @Autowired UserRegisterService userRegisterService;
    @Autowired UserDao userDao;
    List<UserVO> users;

    @Before
    public void setUp() {
        users = Arrays.asList(
                new UserVO("user1", "pass1", "kim", "F", "test1@test.com", "nickname1"),
                new UserVO("user2", "pass2", "song", "F", "lovelydays95@gmail.com", "nickname2"),
                new UserVO("user1", "pass3", "lee", "F", "test3@test.com", "nickname3"),
                new UserVO("user4", "pass4", "kim", "F", "hello2234@naver.com", "nickname4"),
                new UserVO("user5", "pass5", "kim", "F", "user@.invalid.com", "nickname4")
        );
    }

    @Test
    public void addUser() {
        int userCount = userDao.getUserCount();
        userRegisterService.addUser(users.get(0));
        assertThat(userDao.getUserCount(), is(userCount + 1));
    }

    @Test(expected = DuplicateUserIdException.class)
    public void duplicateIdFailure() {
        userRegisterService.addUser(users.get(0));
        userRegisterService.confirmUserId(users.get(2).getId());

    }

    @Test(expected = DuplicateUserEmailException.class)
    public void duplicateEmailFailure() {
        userRegisterService.confirmUserEmail(users.get(1).getEmail());
    }

    @Test
    public void registerEmailSend() {
        userRegisterService.addUser(users.get(3));
    }

    @Test(expected = InvalidEmailAddressException.class)
    public void invalidEmailAddressFailure() {
        userRegisterService.confirmUserEmail(users.get(4).getEmail());
    }
}
