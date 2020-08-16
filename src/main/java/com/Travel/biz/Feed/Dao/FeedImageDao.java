package com.Travel.biz.Feed.Dao;

import com.Travel.biz.Feed.VO.FeedImages;

import java.util.List;

public interface FeedImageDao {
    void addImagePath(FeedImages feedImages);
    void updateImagePath(FeedImages feedImages);
    void deleteImagePath(int feedSid);
    FeedImages getImagePaths(int feedSid);
}
