package com.Travel.Core.User.Dao;

import com.Travel.Core.User.VO.FollowVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FollowDaoJdbc implements FollowDao{
    @Autowired
    @Qualifier("sqlSession")
    private SqlSession sqlSession;

    public void follow(FollowVO follow) {
        sqlSession.insert("follow", follow);
    }

    public void unFollow(FollowVO follow) {
        sqlSession.insert("unFollow", follow);
    }

    public List<Integer> getFollowings(int userSid) {
        return sqlSession.selectList("getFollowings", userSid);
    }

    public List<Integer> getFollowers(int userSid) {
        return sqlSession.selectList("getFollowers", userSid);
    }
}
