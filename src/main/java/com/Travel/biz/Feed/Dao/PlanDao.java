package com.Travel.biz.Feed.Dao;

import com.Travel.biz.Feed.VO.Plan;

import java.util.List;

public interface PlanDao {
    int addPlan(Plan plan);
    void updatePlan(Plan plan);
    void deletePlan(int planId);
    List<Plan> getPlans(int feedId);
}
