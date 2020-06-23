package com.Travel.biz.User;

import com.Travel.Config.Database.MyBatis.MyBatisConfig;
import com.Travel.biz.User.Dao.UserDao;
import com.Travel.biz.User.Domain.User;
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
    private User user1;
    private User user2;

    @Before
    public void setUP(){
        user1 = new User("user1", "user1", "kim", "F", "test@test.com");
        user2 = new User("user2", "user2", "song", "M", "test1@test.com");
    }

    @Test
    public void addUser(){
        userDao.add(user1);
        userDao.add(user2);
    }
}
