package com.Travel.biz.User.Service.Register;

import com.Travel.biz.User.VO.UserVO;

public interface UserRegisterService {
    void addUser(UserVO user);
    void confirmUserId(String id);
    void confirmUserEmail(String email);
}
