package com.Travel.biz.User;

import com.Travel.init.ContextMyBatis;
import com.Travel.init.ContextMail;
import com.Travel.biz.User.Dao.UserDao;
import com.Travel.biz.User.Service.Info.PasswordValidationFailureException;
import com.Travel.biz.User.Service.Info.PasswordValueNotChangedException;
import com.Travel.biz.User.Service.Info.UserInfoService;
import com.Travel.biz.User.Service.Register.UserRegisterService;
import com.Travel.biz.User.VO.UserVO;
import com.Travel.init.RootContextConfiguration;
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
@ContextConfiguration(classes= {RootContextConfiguration.class, ContextMyBatis.class, ContextMail.class})
public class UserInfoServiceTest {
    @Autowired UserInfoService userInfoService;
    @Autowired UserRegisterService userRegisterService;
    @Autowired UserDao userDao;
    List<UserVO> users;

    @Before
    public void setUp(){
        users = Arrays.asList(
                UserVO.builder().id("user1").pass("pass1").name("kim").gender("F").email("test1@test.com").nickname("nickname1").build(),
                UserVO.builder().id("user2").pass("pass2").name("song").gender("F").email("test2@test.com").nickname("nickname2").build(),
                UserVO.builder().id("user3").pass("pass3").name("lee").gender("F").email("test3@test.com").nickname("nickname3").build(),
                UserVO.builder().id("user4").pass("pass4").name("kim").gender("F").email("test4@test.com").nickname("nickname4").build(),
                UserVO.builder().id("user5").pass("pass5").name("park").gender("F").email("test5@test.com").nickname("nickname5").build()
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
