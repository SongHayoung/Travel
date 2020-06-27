package com.Travel.biz.UserService;

import com.Travel.biz.User.Dao.NoSuchUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.Travel.biz.User.Dao.UserDao;
import com.Travel.biz.User.VO.UserVO;
import com.Travel.Init.RootContextConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("core")
@RunWith(SpringJUnit4ClassRunner.class)
@Rollback
@Transactional(propagation= Propagation.REQUIRES_NEW)
@ContextConfiguration(classes= {RootContextConfiguration.class})
public class UserDaoTest {
    @Autowired UserDao userDao;
    static final Logger logger = LoggerFactory.getLogger(UserDaoTest.class);
    private UserVO user1;
    private UserVO user2;

    @Before
    public void setUP(){
        user1 = UserVO.builder().id("user1").pass("user1").name("kim").gender("F").email("test@test.com").nickname("nickname1").build();
        user2 = UserVO.builder().id("user2").pass("user2").name("song").gender("M").email("test1@test.com").nickname("nickname1").build();

        logger.info("Contents : {}", "Logger Test");
    }

    @Test
    public void addUser() {
        userDao.addUser(user1);
        System.out.println(user1.getGender());
        userDao.addUser(user2);
    }

    @Test(expected = NoSuchUserException.class)
    public void noSuchUser() {
        UserVO user = userDao.getUser("noSuchUser");
    }
}
