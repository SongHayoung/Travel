package com.Travel.biz.User;

import com.Travel.init.ContextMyBatis;
import com.Travel.init.ContextMail;
import com.Travel.biz.User.Dao.UserDao;
import com.Travel.biz.User.Service.Register.DuplicateUserEmailException;
import com.Travel.biz.User.Service.Register.DuplicateUserIdException;
import com.Travel.biz.User.Service.Register.InvalidEmailAddressException;
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
public class UserRegisterServiceTest {
    @Autowired UserRegisterService userRegisterService;
    @Autowired UserDao userDao;
    List<UserVO> users;

    @Before
    public void setUp() {
        users = Arrays.asList(
                UserVO.builder().id("user1").pass("pass1").name("kim").gender("F").email("test1@test.com").nickname("nickname1").build(),
                UserVO.builder().id("user2").pass("pass2").name("song").gender("F").email("lovelydays95@gmail.com").nickname("nickname2").build(),
                UserVO.builder().id("user1").pass("pass3").name("lee").gender("F").email("test3@test.com").nickname("nickname3").build(),
                UserVO.builder().id("user4").pass("pass4").name("kim").gender("F").email("hello2234@naver.com").nickname("nickname4").build(),
                UserVO.builder().id("user5").pass("pass5").name("kim").gender("F").email("user@.invalid.com").nickname("nickname4").build()
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
