package com.Travel.Core.User.Service;

import com.Travel.Core.User.VO.UserVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserCoreService {
    @Transactional(readOnly = true)
    UserVO getUserByID(String id);
    void follow(String follower, String following);
    void unFollow(String follower, String following);
    List<UserVO> getFollowings(String id);
}
