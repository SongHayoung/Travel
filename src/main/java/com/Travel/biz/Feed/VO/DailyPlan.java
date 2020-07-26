package com.Travel.biz.Feed.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyPlan {
    private int dailyPlanSid;
    private int planSid;
    private String time;
    private String plan;
}
