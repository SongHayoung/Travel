package com.Travel.biz.Feed.Dao;

import com.Travel.biz.Feed.VO.Feed;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeedDaoJdbc implements FeedDao {
    @Autowired
    @Qualifier("sqlSession")
    private SqlSession sqlSession;

    public int addFeed(Feed feed) {
      sqlSession.insert("insertFeed", feed);
      return feed.getFeedSid();
    }
    public void updateFeed(Feed feed) {
        sqlSession.update("updateFeed", feed);
    }
    public void deleteFeed(int feedSid) {
        sqlSession.insert("deleteFeed", feedSid);
    }
    public Feed getFeed(int feedSid) {
        return sqlSession.selectOne("getFeed", feedSid);
    }
    public List<Feed> getFeeds(String userId) {
        return sqlSession.selectList("getFeedsByUserID", userId);
    }
}
