package com.Travel.biz.MyPage.Service.Info;

import com.Travel.Core.Annotations.TODO;
import com.Travel.Core.User.Dao.FollowDao;
import com.Travel.Core.User.Dao.UserDao;
import com.Travel.Core.User.VO.UserVO;
import com.Travel.biz.MyPage.Dto.UserServiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired UserDao userDao;
    @Autowired FollowDao followDao;
    private Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);
    public boolean updateUserNickNameByID(String userId, String nickName) {
        UserVO targetUser = userDao.getUser(userId);
        targetUser.setNickname(nickName);
        userDao.updateUser(targetUser);
        return true;
    }

    public boolean updateUserPasswordByID(String userId, UserServiceDto.ChangePass userNewPassword)
            throws IncorrectException, NotChangedException {
        UserVO targetUser = userDao.getUser(userId);
        //현재 비밀번호가 옳지 않을 때
        if(targetUser.getPassword().equals(userNewPassword.getCurrentPassword()) == false)
            throw  new IncorrectException();
        //현재 비밀번호가 새로운 비밀번호랑와 일치할 때
        if(userNewPassword.getCurrentPassword().equals(userNewPassword.getNewPassword()) == true)
            throw new NotChangedException();
        targetUser.setPassword(userNewPassword.getNewPassword());
        userDao.updateUser(targetUser);

        return true;
    }

    public void deleteUserByID(String userId) {
        UserVO targetUser = userDao.getUser(userId);
        userDao.deleteUser(userId);
        deleteFollowings(targetUser.getUserSid());
        deleteFollowers(targetUser.getUserSid());
    }

    private void deleteFollowings(int userSid) {
        List<Integer> followings = followDao.getFollowings(userSid);

        for(Integer following : followings)
            userDao.deleteFollowing(following);
    }

    private void deleteFollowers(int userSid) {
        List<Integer> followers = followDao.getFollowers(userSid);

        for(Integer follower : followers)
            userDao.deleteFollower(follower);
    }


    @Transactional(readOnly = true)
    public UserVO loginUser(UserServiceDto.Login loginAccount) throws IncorrectException {
        UserVO targetUser = userDao.getUser(loginAccount.getId());
        if(targetUser.getPassword().equals(loginAccount.getPassword()) == false)
            throw new IncorrectException();

        return targetUser;
    }
}
