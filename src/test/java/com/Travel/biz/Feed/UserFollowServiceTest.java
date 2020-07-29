package com.Travel.biz.Feed;

import com.Travel.Core.User.Service.UserCoreService;
import com.Travel.Core.User.VO.UserVO;
import com.Travel.Init.Core.CoreConfiguration;
import com.Travel.Init.UserService.UserServiceContextConfiguration;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(rollbackFor = Exception.class)
@ContextConfiguration(classes = {CoreConfiguration.class, UserServiceContextConfiguration.class})
public class UserFollowServiceTest {
    @Autowired
    UserCoreService userCoreService;
    @Autowired
    UserRegisterService userRegisterService;

    private Logger logger = LoggerFactory.getLogger(FeedDaoTest.class);
    List<UserVO> users;

    @Before
    public void setUp(){
        users = new ArrayList<>(Arrays.asList(
                UserVO.builder().name("user1").id("user1").email("user1@user.com").password("pass1").gender("F").nickname("user1").roles(new ArrayList<>(
                        Arrays.asList("ROLE_USER"))).build(),
                UserVO.builder().name("user2").id("user2").email("user2@user.com").password("pass2").gender("M").nickname("user2").roles(new ArrayList<>(
                        Arrays.asList("ROLE_USER"))).build(),
                UserVO.builder().name("user3").id("user3").email("user3@user.com").password("pass3").gender("M").nickname("user2").roles(new ArrayList<>(
                        Arrays.asList("ROLE_USER"))).build()
        ));
    }

    @Test
    public void followTest() {
        for(UserVO user : users)
            userRegisterService.addUser(user);

        userCoreService.follow("user1", "user2");
        userCoreService.follow("user1", "user3");

        List<UserVO> followings1 = userCoreService.getFollowings("user1");
        assertThat(followings1.get(0).getId(), is(users.get(1).getId()));
        assertThat(followings1.get(1).getId(), is(users.get(2).getId()));

        UserVO followerUser = userCoreService.getUserByID("user1");
        assertThat(followerUser.getFollowings(), is(2));
        assertThat(followerUser.getFollowers(), is(0));

        UserVO followingUser = userCoreService.getUserByID("user2");
        assertThat(followingUser.getFollowings(), is(0));
        assertThat(followingUser.getFollowers(), is(1));

        userCoreService.unFollow("user1", "user3");

        List<UserVO> followings2 = userCoreService.getFollowings("user1");
        assertThat(followings2.get(0).getId(), is(users.get(1).getId()));
    }
}
