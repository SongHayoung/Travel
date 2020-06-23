package com.Travel.biz.User.Dao;

import com.Travel.biz.User.VO.UserVO;

import java.util.List;

public interface UserDao {
    void addUser(UserVO user);
    UserVO getUser(String id);
    void updateUser(UserVO user);
    void deleteUser(UserVO user);
    int getUserCount();
    int getIdExists(String id);
    int getEmailExists(String email);
    List<UserVO> getUserFriend(String id);
}
