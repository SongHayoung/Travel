package com.Travel.biz.FeedService.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plan {
    private int planSid;
    private int feedSid;
    private String date;
    private List<DailyPlan> dailyPlanList;
}
