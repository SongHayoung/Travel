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
        int feedSid = feedDao.addFeed(feed);
        addPlans(feed.getPlans(), feedSid);
        addAreas(feed.getAreas(), feedSid);
    }

    private void addPlans(List<Plan> plans, int feedSid) {
        for(Plan plan : plans) {
            addPlan(plan, feedSid);
        }
    }

    private void addPlan(Plan plan, int feedSid) {
        plan.setFeedSid(feedSid);
        int planSid = planDao.addPlan(plan);
        addDailyPlans(plan.getDailyPlanList(), planSid);
    }

    private void addDailyPlans(List<DailyPlan> dailyPlans, int planSid) {
        for(DailyPlan dailyPlan : dailyPlans){
            dailyPlan.setPlanSid(planSid);
            dailyPlanDao.addDailyPlan(dailyPlan);
        }
    }

    private void addAreas(List<FeedAreas> areas, int feedSid) {
        for(FeedAreas feedAreas : areas)
            feedAreas.setFeedSid(feedSid);
        feedAreaDao.addAreas(areas);
    }

    public void deleteFeed(int feedSid) {
        feedDao.deleteFeed(feedSid);
    }

    public void updateFeed(Feed feed) {
        feedDao.updateFeed(feed);
        updatePlans(feed.getPlans());
        updateFeedAreas(feed.getAreas(), feed.getFeedSid());
    }

    private void updatePlans(List<Plan> plans) {
        for(Plan plan : plans) {
            planDao.updatePlan(plan);
            updateDailyPlans(plan.getDailyPlanList());
        }
    }

    private void updateFeedAreas(List<FeedAreas> feedAreas, int feedSid) {
        feedAreaDao.deleteAreas(feedAreaDao.getAreas(feedSid));
        feedAreaDao.addAreas(feedAreas);
    }

    private void updateDailyPlans(List<DailyPlan> dailyPlans) {
        for(DailyPlan dailyPlan : dailyPlans){
            dailyPlanDao.updateDailyPlan(dailyPlan);
        }
    }

    public Feed getFeed(int feedSid) {
        Feed feed = feedDao.getFeed(feedSid);

        return getFeedDetail(feed);
    }

    public List<Feed> getUserFeeds(String userId) {
        List<Feed> feeds = feedDao.getFeeds(userId);

        /*
        for(int i = 0; i < feeds.size(); ++i)
            getFeedDetail(feeds.get(i));
         */

        return feeds;
    }

    private Feed getFeedDetail(Feed feed) {
        feed.setUserId(userDao.getUser(
                feed.getUserSid())
                .getId());
        feed.setAreas(feedAreaDao.getAreas(
                feed.getFeedSid()));
        feed.setPlans(
                getPlans(feed.getFeedSid()));

        return feed;
    }

    private List<Plan> getPlans(int feedSid) {
        List<Plan> plans = planDao.getPlans(feedSid);
        for(int i = 0; i < plans.size(); ++i) {
            plans.get(i).setDailyPlanList(
                    getDailyPlans(plans.get(i).getPlanSid()));
        }

        return plans;
    }

    private List<DailyPlan> getDailyPlans(int planSid) {
        return dailyPlanDao.getDailyPlans(planSid);
    }

    public List<Feed> getFollowingUserFeeds(String userSid, Timestamp timestamp, int requestTimes) {
        HashMap<String, Object> followingUserObj = setHashMapObject(userSid, timestamp, requestTimes);

        List<Feed> feeds = feedDao.getUserListFeeds(followingUserObj);

        /*
        for(Feed feed : feeds)
            feed = getFeedDetail(feed);
            */

        return feeds;
    }

    private List<Integer> getFollowingUserSids(String userSid) {
        List<UserVO> followingUsers = userCoreService.getFollowings(userSid);
        List<Integer> followingUserSids = new ArrayList<>();
        for (UserVO user : followingUsers)
            followingUserSids.add(user.getUserSid());

        return followingUserSids;
    }

    private HashMap<String, Object> setHashMapObject(String userSid, Timestamp timestamp, int requestTimes) {
        List<Integer> followingUserSids = getFollowingUserSids(userSid);

        HashMap<String, Object> followingUserObj = new HashMap<>();
        followingUserObj.put("followingUserSids", followingUserSids);
        followingUserObj.put("timestamp", timestamp);
        followingUserObj.put("requestTimes", 10 * requestTimes);

        return followingUserObj;
    }
}
