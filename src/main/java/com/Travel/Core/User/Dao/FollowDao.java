package com.Travel.Core.User.Dao;

import com.Travel.Core.User.VO.FollowVO;

import java.util.List;

public interface FollowDao {
    void follow(FollowVO follow);
    void unFollow(FollowVO follow);
    List<Integer> getFollowings(int userSid);
}
