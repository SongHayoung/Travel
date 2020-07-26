package com.Travel.biz.Feed;

import com.Travel.Init.Core.CoreConfiguration;
import com.Travel.Init.FeedService.FeedServiceContextConfiguration;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FeedServiceContextConfiguration.class, CoreConfiguration.class})
@Transactional
public class FeedServiceDaoTest {
    @Autowired FeedDao feedDao;
    @Autowired PlanDao planDao;
    @Autowired DailyPlanDao dailyPlanDao;
    @Autowired FeedAreaDao feedAreaDao;

    private Logger logger = LoggerFactory.getLogger(FeedServiceDaoTest.class);

    Feed feed1;
    List<FeedAreas> feed_areas1 = new ArrayList<FeedAreas>();
    Plan plan1, plan2;
    List<DailyPlan> dailyPlanList1 = new ArrayList<DailyPlan>(), dailyPlanList2 = new ArrayList<DailyPlan>();
    SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dailyPlanList1.add(DailyPlan.builder().time("09:00").plan("서울").build());
        dailyPlanList1.add(DailyPlan.builder().time("16:00").plan("부산").build());
        dailyPlanList2.add(DailyPlan.builder().time("10:00").plan("부산").build());
        dailyPlanList2.add(DailyPlan.builder().time("18:00").plan("서울").build());
        plan1 = Plan.builder().date("2020/7/1").dailyPlanList(dailyPlanList1).build();
        plan2 = Plan.builder().date("2020/7/2").dailyPlanList(dailyPlanList2).build();
        feed_areas1.add(FeedAreas.builder().tagIds("부산").build());
        feed_areas1.add(FeedAreas.builder().tagIds("서울").build());
        feed_areas1.add(FeedAreas.builder().tagIds("울산").build());
        feed1 = Feed.builder()
                .userId("TravelAdmin")
                .likes(0)
                .contents("테스트 여행 후기")
                .areas(feed_areas1)
                .plans(new ArrayList<>(Arrays.asList(new Plan[]{plan1, plan2})))
                .build();
    }

    @Test
    public void addFeed() {
        feedDao.addFeed(feed1);
        List<Feed> feedList = feedDao.getFeeds("TravelAdmin");
        assertThat(feedList.get(0).getContents(), is("테스트 여행 후기"));
        assertThat(feedList.get(0).getLikes(), is(0));
        feedDao.deleteFeed(feedList.get(0).getFeedSid());
    }

    @Test
    public void addPlan() {
        int feedId = feedDao.addFeed(feed1);
        for(Plan plan : feed1.getPlans()) {
            plan.setFeedSid(feedId);
            planDao.addPlan(plan);
        }
    }

    @Test
    public void addDailyPlan() {
        int feedId = feedDao.addFeed(feed1);
        for(Plan plan : feed1.getPlans()) {
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
        int feedId = feedDao.addFeed(feed1);
        for(Plan plan : feed1.getPlans()) {
            plan.setFeedSid(feedId);
            int planId = planDao.addPlan(plan);
            for(DailyPlan dailyPlan : plan.getDailyPlanList()){
                dailyPlan.setPlanSid(planId);
                dailyPlanDao.addDailyPlan(dailyPlan);
            }
        }
        for(FeedAreas feedAreas : feed1.getAreas())
            feedAreas.setFeedSid(feedId);
        feedAreaDao.addAreas(feed1.getAreas());
        for(FeedAreas feedAreas1 : feedAreaDao.getAreas(feedId))
            System.out.println(feedAreas1.getTagIds());
        List<FeedAreas> feedArea = new ArrayList<FeedAreas>();
        feedArea.add(FeedAreas.builder().tagIds("부산").feedSid(feedId).build());
        feedArea.add(FeedAreas.builder().tagIds("울산").feedSid(feedId).build());
        feedAreaDao.deleteAreas(feedArea);
    }

    @Test
    @Transactional
    public void getFeed() {
        int feedId = feedDao.addFeed(feed1);
        for(Plan plan : feed1.getPlans()) {
            plan.setFeedSid(feedId);
            int planId = planDao.addPlan(plan);
            for(DailyPlan dailyPlan : plan.getDailyPlanList()){
                dailyPlan.setPlanSid(planId);
                dailyPlanDao.addDailyPlan(dailyPlan);
            }
        }
        for(FeedAreas feedAreas : feed1.getAreas())
            feedAreas.setFeedSid(feedId);
        feedAreaDao.addAreas(feed1.getAreas());

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
            System.out.println("Tag Ids : " + area.getTagIds());


        for(Plan plan : feed.getPlans()){
            System.out.println(plan.getDate());
            for(DailyPlan dailyPlan : plan.getDailyPlanList())
                System.out.println(dailyPlan.getTime() + " " + dailyPlan.getPlan());
        }
    }
}
