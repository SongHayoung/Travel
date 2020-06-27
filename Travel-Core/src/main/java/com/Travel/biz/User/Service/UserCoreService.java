package com.Travel.biz.User.Service;

import com.Travel.biz.User.VO.UserVO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserCoreService {

    @Transactional(readOnly = true)
    UserVO getUserByID(String id);
}
