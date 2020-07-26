package com.Travel.Core.User.Service;

import com.Travel.Core.User.Dao.FollowDao;
import com.Travel.Core.User.Dao.UserDao;
import com.Travel.Core.User.VO.FollowVO;
import com.Travel.Core.User.VO.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCoreServiceImpl implements UserCoreService{
    @Autowired
    UserDao userDao;
    @Autowired
    FollowDao followDao;
    public UserVO getUserByID(String id) {
        return userDao.getUser(id);
    }

    public void follow(String follower, String following) {
        UserVO followerUser = getUserByID(follower);
        UserVO followingUser = getUserByID(following);
        followDao.follow(FollowVO.builder().follower(followerUser.getUserSid()).following(followingUser.getUserSid()).build());
    }
    public void unFollow(String follower, String following) {
        UserVO followerUser = getUserByID(follower);
        UserVO followingUser = getUserByID(following);
        followDao.follow(FollowVO.builder().follower(followerUser.getUserSid()).following(followingUser.getUserSid()).build());
    }

    public List<UserVO> getFollowings(String id) {
        return userDao.getUserFollowings(
                followDao.getFollowings(
                        userDao.getUser(id).getUserSid()
                ));
    }
}
