package com.Travel.biz.Feed;

import com.Travel.Init.Core.CoreConfiguration;
import com.Travel.Init.FeedService.FeedServiceContextConfiguration;
import com.Travel.Init.UserService.UserServiceContextConfiguration;
import com.Travel.biz.Feed.Service.FeedService;
import com.Travel.biz.Feed.VO.DailyPlan;
import com.Travel.biz.Feed.VO.Feed;
import com.Travel.biz.Feed.VO.FeedAreas;
import com.Travel.biz.Feed.VO.Plan;
import com.Travel.biz.MyPage.Service.Register.UserRegisterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FeedServiceContextConfiguration.class, CoreConfiguration.class, UserServiceContextConfiguration.class})
@Transactional
public class FeedServiceTest {
    @Autowired
    UserRegisterService userRegisterService;

    @Autowired
    FeedService feedService;

    private Logger logger = LoggerFactory.getLogger(FeedServiceTest.class);

    List<Feed> feeds;
    Feed detailedFeed;

    @Before
    public void setUp() {
        feeds = Arrays.asList(
                Feed.builder().userId("user2").likes(0).contents("test user2 feed1").build(),
                Feed.builder().userId("user2").likes(0).contents("test user2 feed2").build(),
                Feed.builder().userId("user2").likes(0).contents("test user2 feed3").build(),
                Feed.builder().userId("user3").likes(0).contents("test user3 feed1").build(),
                Feed.builder().userId("user3").likes(0).contents("test user3 feed2").build(),
                Feed.builder().userId("user3").likes(0).contents("test user3 feed3").build()
        );

        detailedFeed = Feed.builder()
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
    }

    @Test
    public void getDetailedFeed() {
        feedService.addFeed(detailedFeed);
        feedService.addFeed(detailedFeed);
        feedService.addFeed(detailedFeed);
        feedService.addFeed(detailedFeed);
        feedService.addFeed(detailedFeed);
        feedService.addFeed(detailedFeed);
        feedService.addFeed(detailedFeed);
        feedService.addFeed(detailedFeed);
        feedService.addFeed(detailedFeed);
        feedService.addFeed(detailedFeed);
        feedService.addFeed(detailedFeed);
        feedService.addFeed(detailedFeed);
        feedService.addFeed(detailedFeed);
        feedService.addFeed(detailedFeed);
        feedService.addFeed(detailedFeed);
        feedService.addFeed(detailedFeed);
        feedService.addFeed(detailedFeed);
        feedService.addFeed(detailedFeed);
        List<Feed> feeds = feedService.getUserFeeds("TravelAdmin");
        assertThat(feeds.size(), is(18));
        for(Feed feed : feeds) {
            assertThat(feed.getContents(), is(detailedFeed.getContents()));
            assertThat(feed.getUserId(), is(detailedFeed.getUserId()));
            assertThat(feed.getLikes(), is(detailedFeed.getLikes()));
            int feedAreaIndex = 0;
            for(FeedAreas feedArea : feed.getAreas()) {
                assertThat(feedArea.getTagId(), is(detailedFeed.getAreas().get(feedAreaIndex++).getTagId()));
            }
            int planIndex = 0;
            for(Plan plan : feed.getPlans()) {
                assertThat(plan.getDate(), is(detailedFeed.getPlans().get(planIndex).getDate()));
                int dailyPlainIndex = 0;
                for(DailyPlan dailyPlan : plan.getDailyPlanList()) {
                    assertThat(dailyPlan.getTime(), is(detailedFeed.getPlans().get(planIndex).getDailyPlanList().get(dailyPlainIndex).getTime()));
                    assertThat(dailyPlan.getPlan(), is(detailedFeed.getPlans().get(planIndex).getDailyPlanList().get(dailyPlainIndex).getPlan()));
                    dailyPlainIndex++;
                }
                planIndex++;
            }
        }
    }
}
