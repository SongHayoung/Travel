package com.Travel.Core.User.Dao;

import com.Travel.Core.User.VO.FollowVO;
import com.Travel.Core.User.VO.UserVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDao {
    void addUser(UserVO user);
    void updateUser(UserVO user);
    void deleteUser(UserVO user);
    UserVO getUser(String id) throws NoValueException;
    UserVO getUser(int userSid) throws NoValueException;
    int getUserCount();
    void isIdExists(String id) throws DuplicateKeyException;
    void isEmailExists(String email) throws DuplicateKeyException;
    List<UserVO> getUserFollowings(List<Integer> follows);
    void addFollowing(Integer userSid);
    void deleteFollowing(Integer userSid);
    void addFollower(Integer userSid);
    void deleteFollower(Integer userSid);
}
