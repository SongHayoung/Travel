package com.Travel.biz.FeedService.Dao;

import com.Travel.biz.FeedService.VO.DailyPlan;

import java.util.List;

public interface DailyPlanDao {
    void addDailyPlan(DailyPlan dailyPlan);
    void updateDailyPlan(DailyPlan dailyPlan);
    void deleteDailyPlan(int dailyPlanSid);
    List<DailyPlan> getDailyPlans(int planSid);
}
