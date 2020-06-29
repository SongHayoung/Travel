package com.Travel.Core.User.Service;

import com.Travel.Core.User.Dao.UserDao;
import com.Travel.Core.User.VO.UserVO;
import org.springframework.beans.factory.annotation.Autowired;

public class UserCoreServiceImpl {
    @Autowired UserDao userDao;

    public UserVO getUserByID(String id) {
        return userDao.getUser(id);
    }
}
