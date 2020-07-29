package com.Travel.biz.Feed.Service;

import com.Travel.Core.User.Dao.UserDao;
import com.Travel.Core.User.Service.UserCoreService;
import com.Travel.Core.User.VO.UserVO;
import com.Travel.biz.Feed.Dao.DailyPlanDao;
import com.Travel.biz.Feed.Dao.FeedAreaDao;
import com.Travel.biz.Feed.Dao.FeedDao;
import com.Travel.biz.Feed.Dao.PlanDao;
import com.Travel.biz.Feed.VO.DailyPlan;
import com.Travel.biz.Feed.VO.Feed;
import com.Travel.biz.Feed.VO.FeedAreas;
import com.Travel.biz.Feed.VO.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class FeedServiceImpl implements FeedService{
    @Autowired
    FeedDao feedDao;
    @Autowired
    PlanDao planDao;
    @Autowired
    DailyPlanDao dailyPlanDao;
    @Autowired
    FeedAreaDao feedAreaDao;
    @Autowired
    UserCoreService userCoreService;
    @Autowired
    UserDao userDao;

    public void addFeed(Feed feed) {
        int feedId = feedDao.addFeed(feed);
        for(Plan plan : feed.getPlans()) {
            plan.setFeedSid(feedId);
            int planId = planDao.addPlan(plan);
            for(DailyPlan dailyPlan : plan.getDailyPlanList()){
                dailyPlan.setPlanSid(planId);
                dailyPlanDao.addDailyPlan(dailyPlan);
            }
        }
        for(FeedAreas feedAreas : feed.getAreas())
            feedAreas.setFeedSid(feedId);
        feedAreaDao.addAreas(feed.getAreas());
    }

    public void deleteFeed(int feedSid) {
        feedDao.deleteFeed(feedSid);
    }

    public void updateFeed(Feed feed) {
        feedDao.updateFeed(feed);
        for(Plan plan : feed.getPlans()) {
            planDao.updatePlan(plan);
            for(DailyPlan dailyPlan : plan.getDailyPlanList()){
                dailyPlanDao.updateDailyPlan(dailyPlan);
            }
        }
        feedAreaDao.deleteAreas(feedAreaDao.getAreas(feed.getFeedSid()));
        feedAreaDao.addAreas(feed.getAreas());
    }

    public Feed getFeed(int feedSid) {
        Feed feed = feedDao.getFeed(feedSid);

        return getFeedDetail(feed);
    }

    public List<Feed> getUserFeeds(String userId) {
        List<Feed> feeds = feedDao.getFeeds(userId);

        for(int i = 0; i < feeds.size(); ++i)
            getFeedDetail(feeds.get(i));

        return feeds;
    }

    private Feed getFeedDetail(Feed feed) {
        feed.setUserId(userDao.getUser(feed.getUserSid()).getId());
        feed.setAreas(feedAreaDao.getAreas(feed.getFeedSid()));
        List<Plan> plans = planDao.getPlans(feed.getFeedSid());
        for(int i = 0; i < plans.size(); ++i) {
            plans.get(i).setDailyPlanList(
                    dailyPlanDao.getDailyPlans(
                            plans.get(i).getPlanSid()));
        }
        feed.setPlans(plans);

        return feed;
    }

    public List<Feed> getFollowingUserFeeds(String userId, Timestamp timestamp, int requestTimes) {
        List<UserVO> followings = userCoreService.getFollowings(userId);
        List<Integer> followingUserSids = new ArrayList<>();
        for(UserVO user : followings)
            followingUserSids.add(user.getUserSid());

        HashMap<String, Object> followingUserObj = new HashMap<>();
        followingUserObj.put("followingUserSids", followingUserSids);
        followingUserObj.put("timestamp", timestamp);
        followingUserObj.put("requestTimes", 10 * requestTimes);

        List<Feed> feeds = feedDao.getUserListFeeds(followingUserObj);

        for(Feed feed : feeds) {
            feed = getFeedDetail(feed);
        }

        return feeds;
    }
}
