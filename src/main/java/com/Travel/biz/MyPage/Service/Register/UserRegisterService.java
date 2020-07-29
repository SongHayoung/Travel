package com.Travel.biz.MyPage.Service.Register;

import com.Travel.Core.User.VO.UserVO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRegisterService {
    void addUser(UserVO user);
    void isDuplicateEmail(String email);
    void isDuplicateId(String userId);
    void sendRegisterMail(String email, String authNum);
}
