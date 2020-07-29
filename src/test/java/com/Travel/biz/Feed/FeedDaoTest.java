package com.Travel.biz.Feed;

import com.Travel.Core.User.Dao.UserDao;
import com.Travel.Core.User.Service.UserCoreService;
import com.Travel.Core.User.VO.UserVO;
import com.Travel.Init.Core.CoreConfiguration;
import com.Travel.Init.FeedService.FeedServiceContextConfiguration;
import com.Travel.Init.UserService.UserServiceContextConfiguration;
import com.Travel.biz.Feed.Dao.DailyPlanDao;
import com.Travel.biz.Feed.Dao.FeedAreaDao;
import com.Travel.biz.Feed.Dao.FeedDao;
import com.Travel.biz.Feed.Dao.PlanDao;
import com.Travel.biz.Feed.VO.DailyPlan;
import com.Travel.biz.Feed.VO.Feed;
import com.Travel.biz.Feed.VO.FeedAreas;
import com.Travel.biz.Feed.VO.Plan;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FeedServiceContextConfiguration.class, CoreConfiguration.class, UserServiceContextConfiguration.class})
@Transactional
public class FeedDaoTest {
    @Autowired UserDao userDao;
    @Autowired FeedDao feedDao;
    @Autowired PlanDao planDao;
    @Autowired DailyPlanDao dailyPlanDao;
    @Autowired FeedAreaDao feedAreaDao;
    @Autowired UserCoreService userCoreService;
    private Logger logger = LoggerFactory.getLogger(FeedDaoTest.class);

    Feed feed;

    List<UserVO> users;
    List<Feed> feeds;
    @Before
    public void setUp() {
        feed = Feed.builder()
                .userId("TravelAdmin")
                .likes(0)
                .contents("테스트 여행 후기")
                .areas(Arrays.asList(
                        FeedAreas.builder().tagId("부산").build(),
                        FeedAreas.builder().tagId("서울").build(),
                        FeedAreas.builder().tagId("울산").build()))
                .plans(Arrays.asList(
                        Plan.builder().date("2020/7/1").dailyPlanList(Arrays.asList(
                                DailyPlan.builder().time("09:00").plan("서울").build(),
                                DailyPlan.builder().time("16:00").plan("부산").build()
                        )).build(),
                        Plan.builder().date("2020/7/2").dailyPlanList(Arrays.asList(
                                DailyPlan.builder().time("10:00").plan("부산").build(),
                                DailyPlan.builder().time("18:00").plan("서울").build()
                        )).build()))
                .build();

        users = Arrays.asList(
                UserVO.builder().name("user1").id("user1").password("user1").email("user1@test.com").gender("M").nickname("user1")
                        .roles(Arrays.asList("ROLE_USER")).build(),
                UserVO.builder().name("user2").id("user2").password("user2").email("user2@test.com").gender("M").nickname("user2")
                        .roles(Arrays.asList("ROLE_USER")).build(),
                UserVO.builder().name("user3").id("user3").password("user3").email("user3@test.com").gender("M").nickname("user3")
                        .roles(Arrays.asList("ROLE_USER")).build()
        );

        feeds = Arrays.asList(
                Feed.builder().userId("user2").likes(0).contents("test user2 feed1").build(),
                Feed.builder().userId("user2").likes(0).contents("test user2 feed2").build(),
                Feed.builder().userId("user2").likes(0).contents("test user2 feed3").build(),
                Feed.builder().userId("user3").likes(0).contents("test user3 feed1").build(),
                Feed.builder().userId("user3").likes(0).contents("test user3 feed2").build(),
                Feed.builder().userId("user3").likes(0).contents("test user3 feed3").build()
        );

    }

    @Test
    public void addFeed() {
        feedDao.addFeed(feed);
        List<Feed> feedList = feedDao.getFeeds("TravelAdmin");
        assertThat(feedList.get(0).getContents(), is("테스트 여행 후기"));
        assertThat(feedList.get(0).getLikes(), is(0));
        feedDao.deleteFeed(feedList.get(0).getFeedSid());
    }

    @Test
    public void addPlan() {
        int feedId = feedDao.addFeed(feed);
        for(Plan plan : feed.getPlans()) {
            plan.setFeedSid(feedId);
            planDao.addPlan(plan);
        }
    }

    @Test
    public void addDailyPlan() {
        int feedId = feedDao.addFeed(feed);
        for(Plan plan : feed.getPlans()) {
            plan.setFeedSid(feedId);
            int planId = planDao.addPlan(plan);
            for(DailyPlan dailyPlan : plan.getDailyPlanList()){
                dailyPlan.setPlanSid(planId);
                dailyPlanDao.addDailyPlan(dailyPlan);
            }
        }
    }

    @Test
    public void addAreas() {
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

        for(FeedAreas feedAreas1 : feedAreaDao.getAreas(feedId))
            System.out.println(feedAreas1.getTagId());
        List<FeedAreas> feedArea = new ArrayList<FeedAreas>();
        feedArea.add(FeedAreas.builder().tagId("부산").feedSid(feedId).build());
        feedArea.add(FeedAreas.builder().tagId("울산").feedSid(feedId).build());
        feedAreaDao.deleteAreas(feedArea);
    }

    @Test
    public void getFeed() {
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

        Feed feed = feedDao.getFeed(feedId);
        feed.setFeedSid(feedId);
        feed.setAreas(feedAreaDao.getAreas(feedId));
        feed.setPlans(planDao.getPlans(feedId));
        for(Plan plan : feed.getPlans())
            plan.setDailyPlanList(dailyPlanDao.getDailyPlans(plan.getPlanSid()));

        System.out.println(feed.getContents());
        System.out.println(feed.getLikes());
        System.out.println(feed.getUpload_time().toString());
        for(FeedAreas area : feed.getAreas())
            System.out.println("Tag Ids : " + area.getTagId());


        for(Plan plan : feed.getPlans()){
            System.out.println(plan.getDate());
            for(DailyPlan dailyPlan : plan.getDailyPlanList())
                System.out.println(dailyPlan.getTime() + " " + dailyPlan.getPlan());
        }
    }

    @Test
    /**
     * 삽입하는데 약간의 시간 오차가 생길 수 있으므로 마지막 assert에서 맞지 않을 수 있음
     */
    public void getFollowingUserFeeds() {
        for(UserVO user : users)
            userDao.addUser(user);

        userCoreService.follow("user1", "user2");
        userCoreService.follow("user1", "user3");

        for(Feed feed : feeds)
            feedDao.addFeed(feed);

        List<UserVO> followings = userCoreService.getFollowings("user1");
        List<Integer> followingUserSids = new ArrayList<>();
        for(UserVO user : followings)
            followingUserSids.add(user.getUserSid());

        HashMap<String, Object> followingUserObj = new HashMap<>();
        followingUserObj.put("followingUserSids", followingUserSids);
        followingUserObj.put("timestamp", new Timestamp(System.currentTimeMillis()));
        followingUserObj.put("requestTimes", 0);

        List<Feed> followingFeeds = feedDao.getUserListFeeds(followingUserObj);
        assertThat(followingFeeds.get(0).getContents(), is("test user2 feed1"));
        assertThat(followingFeeds.get(1).getContents(), is("test user2 feed2"));
        assertThat(followingFeeds.get(2).getContents(), is("test user2 feed3"));
        assertThat(followingFeeds.get(3).getContents(), is("test user3 feed1"));
        assertThat(followingFeeds.get(4).getContents(), is("test user3 feed2"));
        assertThat(followingFeeds.get(5).getContents(), is("test user3 feed3"));
    }
}
