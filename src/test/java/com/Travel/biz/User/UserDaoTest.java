package com.Travel.biz.User;

import com.Travel.Config.Database.MyBatis.MyBatisConfig;
import com.Travel.biz.User.Dao.UserDao;
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

@RunWith(SpringJUnit4ClassRunner.class)
@Rollback
@Transactional(propagation= Propagation.REQUIRES_NEW)
@ContextConfiguration(classes= {RootConfig.class, MyBatisConfig.class})
public class UserDaoTest {
    @Autowired UserDao userDao;
    private UserVO user1;
    private UserVO user2;

    @Before
    public void setUP(){
        user1 = new UserVO("user1", "user1", "kim", "F", "test@test.com", "nickname1");
        user2 = new UserVO("user2", "user2", "song", "M", "test1@test.com", "nickname1");
    }

    @Test
    public void addUser(){
        userDao.addUser(user1);
        userDao.addUser(user2);
    }
}
