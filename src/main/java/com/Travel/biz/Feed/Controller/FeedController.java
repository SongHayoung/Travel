package com.Travel.biz.Feed.Controller;

import com.Travel.Core.Annotations.TODO;
import com.Travel.Core.Jwt.JwtTokenProvider;
import com.Travel.biz.Feed.Service.FeedService;
import com.Travel.biz.Feed.VO.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@RequestMapping("/feed")
@RestController
public class FeedController {
    @Autowired
    FeedService feedService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @GetMapping("/{feedSid}")
    public Feed getFeedByFeedSid(@PathVariable("feedSid") int feedSid) {
        return feedService.getFeed(feedSid);
    }

    @PostMapping
    @TODO("이미지 처리 로직 필요")
    public String postFeed(@RequestBody Feed feed, @RequestParam("uploadFile") MultipartFile[] files, @RequestHeader("X-AUTH-TOKEN") String token) {
        jwtTokenProvider.validateUser(feed.getUserId(), token);
        feedService.addFeed(feed, files);

        return "success";
    }

    @PatchMapping
    @TODO("feedSid invalid할때 처리 필요")
    public String updateFeed(@RequestBody Feed feed, @RequestParam("uploadFile") MultipartFile[] files, @RequestHeader("X-AUTH-TOKEN") String token) {
        jwtTokenProvider.validateUser(feed.getUserId(), token);
        feedService.updateFeed(feed, files);

        return "success";
    }

    @DeleteMapping("/{feedSid}")
    @TODO("feedSid invalid할때 처리 필요")
    public String deleteFeed(@PathVariable("feedSid") int feedSid, @RequestHeader("X-AUTH-TOKEN") String token) {
        //jwtTokenProvider.validateUser()
        feedService.deleteFeed(feedSid);

        return "success";
    }

    @GetMapping
    @TODO("시간값 요청값 검증 필요")
    public List<Feed> getFollowingFeeds(@RequestBody Timestamp timestamp, @RequestBody int requestTimes, @RequestHeader("X-AUTH-TOKEN") String token) {
        return feedService.getFollowingUserFeeds(jwtTokenProvider.getUserPk(token), timestamp, requestTimes);
    }

    @GetMapping("/user/{userId}")
    @TODO("is Following? 구현 필요 어디다 선언하지")
    public List<Feed> getUserFeed(@PathVariable("userId") String userId) {
        return feedService.getUserFeeds(userId);
    }
}