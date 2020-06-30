package com.Travel.Core.User.Dao;

import com.Travel.Core.User.VO.UserVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDao {
    void addUser(UserVO user);
    void updateUser(UserVO user);
    void deleteUser(UserVO user);
    UserVO getUser(String id) throws NoValueException;
    int getUserCount();
    void isIdExists(String id) throws DuplicateKeyException;
    void isEmailExists(String email) throws DuplicateKeyException;
    List<UserVO> getUserFriend(String id);
}
