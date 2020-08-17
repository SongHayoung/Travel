package com.Travel.biz.Feed.Service;

import com.Travel.biz.Feed.VO.Feed;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@Transactional
public interface FeedService {
    void addFeed(Feed feed, MultipartFile[] multipartFiles);
    void deleteFeed(int feedSid);
    void updateFeed(Feed feed, MultipartFile[] multipartFiles);
    @Transactional(readOnly = true)
    Feed getFeed(int feedSid);
    @Transactional(readOnly = true)
    List<Feed> getUserFeeds(String userId);
    @Transactional(readOnly = true)
    List<Feed> getFollowingUserFeeds(String userId, Timestamp timestamp, int requestTimes);
}
