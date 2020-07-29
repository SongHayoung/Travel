package com.Travel.biz.Feed.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feed {
    private String userId;
    private int feedSid;
    private int userSid;
    private int likes;
    private String contents;
    private Date upload_time;
    private List<Plan> plans;
    private List<FeedAreas> areas;
    private FeedImages images;
}
