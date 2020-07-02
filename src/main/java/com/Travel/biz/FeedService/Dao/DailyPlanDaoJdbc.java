package com.Travel.biz.FeedService.Dao;

import com.Travel.biz.FeedService.VO.DailyPlan;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DailyPlanDaoJdbc implements DailyPlanDao{
    @Autowired
    @Qualifier("sqlSession")
    SqlSession sqlSession;

    public void addDailyPlan(DailyPlan dailyPlan) {
        sqlSession.insert("insertDailyPlan", dailyPlan);
    }

    public void updateDailyPlan(DailyPlan dailyPlan) {
        sqlSession.update("updateDailyPlan", dailyPlan);
    }

    public void deleteDailyPlan(int dailyPlanSid) {
        sqlSession.delete("deleteDailyPlan", dailyPlanSid);
    }

    public List<DailyPlan> getDailyPlans(int planSid) {
        return sqlSession.selectList("getDailyPlans", planSid);
    }
}
