package com.Travel.Core.User.Dao;

import com.Travel.Core.User.VO.UserVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public interface UserDao {
    void addUser(UserVO user);
    void updateUser(UserVO user);
    void deleteUser(UserVO user);

    @Transactional(readOnly = true)
    UserVO getUser(String id) throws NoValueException;

    @Transactional(readOnly = true)
    int getUserCount();

    @Transactional(readOnly = true)
    void isIdExists(String id) throws DuplicateKeyException;

    @Transactional(readOnly = true)
    void isEmailExists(String email) throws DuplicateKeyException;

    @Transactional(readOnly = true)
    List<UserVO> getUserFriend(String id);
}
