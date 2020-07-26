package com.Travel.biz.Feed.Dao;

import com.Travel.biz.Feed.VO.FeedAreas;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeedAreaDaoJdbc implements FeedAreaDao{
    @Autowired
    @Qualifier("sqlSession")
    private SqlSession sqlSession;

    public void addAreas(List<FeedAreas> areas) {
        sqlSession.insert("addFeedAreas", areas);
    }

    public void updateAreas(FeedAreas areas) {
        sqlSession.update("updateFeedAreas", areas);
    }

    public void deleteAreas(List<FeedAreas> areas) {
        for(FeedAreas area : areas)
            sqlSession.delete("deleteFeedAreas", area);
    }

    public List<FeedAreas> getAreas(int feedSid) {
        return sqlSession.selectList("getFeedAreas", feedSid);
    }
}
