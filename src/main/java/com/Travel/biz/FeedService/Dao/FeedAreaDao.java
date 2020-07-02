package com.Travel.biz.FeedService.Dao;

import com.Travel.biz.FeedService.VO.FeedAreas;

import java.util.List;


public interface FeedAreaDao {
    void addAreas(List<FeedAreas> areas);
    void updateAreas(FeedAreas areas);
    void deleteAreas(List<FeedAreas> areas);
    List<FeedAreas> getAreas(int feedSid);
}
