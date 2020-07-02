package com.Travel.biz.FeedService.Dao;

import com.Travel.biz.FeedService.VO.Plan;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlanDaoJdbc implements PlanDao {
    @Autowired
    @Qualifier("sqlSession")
    private SqlSession sqlSession;

    public int addPlan(Plan plan) {
        sqlSession.insert("insertPlan", plan);

        return plan.getPlanSid();
    }
    public void updatePlan(Plan plan) {
        sqlSession.update("updatePlan", plan);
    }

    public void deletePlan(int planId) {
        sqlSession.delete("deletePlan", planId);
    }

    public List<Plan> getPlans(int feedId) {
        return sqlSession.selectList("getPlans", feedId);
    }
}
