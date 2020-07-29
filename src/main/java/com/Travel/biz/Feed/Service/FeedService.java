package com.Travel.biz.Feed.Service;

import com.Travel.biz.Feed.VO.Feed;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Transactional
public interface FeedService {
    void addFeed(Feed feed);
    void deleteFeed(int feedSid);
    void updateFeed(Feed feed);
    @Transactional(readOnly = true)
    Feed getFeed(int feedSid);
    @Transactional(readOnly = true)
    List<Feed> getUserFeeds(String userId);
    @Transactional(readOnly = true)
    List<Feed> getFollowingUserFeeds(String userId, Timestamp timestamp, int requestTimes);
}
