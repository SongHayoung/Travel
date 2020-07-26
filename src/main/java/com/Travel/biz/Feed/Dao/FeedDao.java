package com.Travel.biz.Feed.Dao;

import com.Travel.biz.Feed.VO.Feed;

import java.util.List;


public interface FeedDao {
    int addFeed(Feed feed);
    void updateFeed(Feed feed);
    void deleteFeed(int feedId);
    Feed getFeed(int feedId);
    List<Feed> getFeeds(String userId);
}
