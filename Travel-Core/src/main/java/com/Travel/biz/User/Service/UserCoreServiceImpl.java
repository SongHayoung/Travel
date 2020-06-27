package com.Travel.biz.User.Service;

import com.Travel.biz.User.Dao.UserDao;
import com.Travel.biz.User.VO.UserVO;
import org.springframework.beans.factory.annotation.Autowired;

public class UserCoreServiceImpl {
    @Autowired UserDao userDao;

    public UserVO getUserByID(String id) {
        return userDao.getUser(id);
    }
}
