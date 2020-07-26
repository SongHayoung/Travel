package com.Travel.biz.MyPage.Service.Register;

import com.Travel.Core.User.VO.UserVO;

public interface UserRegisterService {
    void addUser(UserVO user);
    void isDuplicateEmail(String email);
    void isDuplicateId(String userId);
    void sendRegisterMail(String email, String authNum);
}
