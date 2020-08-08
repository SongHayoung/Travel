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
        increaseFollowCount(followerUser, followingUser);
    }

    private void increaseFollowCount(UserVO followerUser, UserVO followingUser) {
        userDao.addFollowing(followerUser.getUserSid());
        userDao.addFollower(followingUser.getUserSid());
    }

    public void unFollow(String follower, String following) {
        UserVO followerUser = getUserByID(follower);
        UserVO followingUser = getUserByID(following);
        followDao.follow(FollowVO.builder().follower(followerUser.getUserSid()).following(followingUser.getUserSid()).build());
        decreaseFollowCount(followerUser, followingUser);
    }

    private void decreaseFollowCount(UserVO followerUser, UserVO followingUser) {
        userDao.deleteFollowing(followerUser.getUserSid());
        userDao.deleteFollower(followingUser.getUserSid());
    }

    public List<UserVO> getFollowings(String id) {
        return userDao.getUserFollowings(
                followDao.getFollowings(
                        userDao.getUser(id).getUserSid()
                ));
    }
}
