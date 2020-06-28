package com.Travel.biz.Area;

import com.Travel.Init.RootContextConfiguration;
import com.Travel.biz.Area.Dao.AreaDao;
import com.Travel.biz.Area.VO.AreaVO;
import com.Travel.biz.User.Dao.UserDao;
import com.Travel.biz.User.VO.UserVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@Rollback
@Transactional(propagation= Propagation.REQUIRES_NEW)
@ContextConfiguration(classes= {RootContextConfiguration.class})
public class AreaDaoTest {
    @Autowired UserDao userDao;
    @Autowired AreaDao areaDao;
    private UserVO user1;
    private AreaVO area1;
    private AreaVO area2;

    @Before
    public void setUP() {
        user1 = UserVO.builder().id("user1").pass("user1").name("kim").gender("F").email("test@test.com").nickname("nickname1").build();
        area1 = AreaVO.builder().id(user1.getId()).areaName("test1").build();
        area2 = AreaVO.builder().id(user1.getId()).areaName("test2").build();
    }

    @Test
    public void getUserFavorits() {
        userDao.addUser(user1);
        areaDao.addArea(area1);
        areaDao.addArea(area2);

        List<String> areas = areaDao.getAreas(user1.getId());
        assertThat(areas.size(), is(2));
        assertThat(areas.get(0), is("test1"));
        assertThat(areas.get(1), is("test2"));

        areaDao.deleteArea(area1);
        areaDao.deleteArea(area2);
        List<String> afterAreas = areaDao.getAreas(user1.getId());
        assertThat(afterAreas.size(), is(0));
    }

    @Test
    public void getAllAreas() {
        List<String> areas = areaDao.getAllAreas();
        assertThat(areas.size(), is(2));
        assertThat(areas.get(0), is("test1"));
        assertThat(areas.get(1), is("test2"));
    }
}
