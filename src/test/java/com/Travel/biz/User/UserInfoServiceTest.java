package com.Travel.biz.User;

import com.Travel.Config.Database.MyBatis.MyBatisConfig;
import com.Travel.Config.Mail.MailConfig;
import com.Travel.biz.Mail.Service.MailService;
import com.Travel.biz.User.Dao.UserDao;
import com.Travel.biz.User.Service.Info.PasswordValidationFailureException;
import com.Travel.biz.User.Service.Info.PasswordValueNotChangedException;
import com.Travel.biz.User.Service.Info.UserInfoService;
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
public class UserInfoServiceTest {
    @Autowired UserInfoService userInfoService;
    @Autowired UserRegisterService userRegisterService;
    @Autowired UserDao userDao;
    List<UserVO> users;

    @Before
    public void setUp(){
        users = Arrays.asList(
                new UserVO("user1", "pass1", "kim", "F", "test1@test.com", "nickname1"),
                new UserVO("user2", "pass2", "song", "F", "test2@test.com", "nickname2"),
                new UserVO("user3", "pass3", "lee", "F", "test3@test.com", "nickname3"),
                new UserVO("user4", "pass4", "kim", "F", "test4@test.com", "nickname4"),
                new UserVO("user5", "pass5", "park", "F", "test5@test.com", "nickname5")
        );
    }

    @Test
    public void changeNickName() {
        for(UserVO user : users)
            userRegisterService.addUser(user);

        userInfoService.updateUserNickNameByID(users.get(0).getId(), "newnickname1");

        assertThat(userInfoService.getUserByID(users.get(0).getId()).getNickname(), is("newnickname1"));
    }

    @Test(expected = PasswordValidationFailureException.class)
    public void passwordValidationFailure() {
        userRegisterService.addUser(users.get(0));

        userInfoService.validateUserPass(users.get(0).getId(), "exceptionpassword");
    }

    @Test(expected = PasswordValueNotChangedException.class)
    public void passwordValueNotChanged() {
        userRegisterService.addUser(users.get(0));

        userInfoService.updateUserPasswordByID(users.get(0).getId(), users.get(0).getPass(), users.get(0).getPass());
    }

    @Test
    public void passwordValueChanged() {
        userRegisterService.addUser(users.get(0));

        userInfoService.updateUserPasswordByID(users.get(0).getId(), users.get(0).getPass(), "newpass1");

        assertThat(userInfoService.getUserByID(users.get(0).getId()).getPass(), is("newpass1"));
    }

    @Test
    public void deleteUser() {
        int prevCount = userDao.getUserCount();
        userRegisterService.addUser(users.get(0));

        int addCount = userDao.getUserCount();
        assertThat(addCount, is(prevCount + 1));
        userInfoService.deleteUserByID(users.get(0).getId());

        assertThat(userDao.getUserCount(), is(prevCount));
    }
}
