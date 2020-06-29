package com.Travel.Core.User.Service;

import com.Travel.Core.User.VO.UserVO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserCoreService {

    @Transactional(readOnly = true)
    UserVO getUserByID(String id);
}
