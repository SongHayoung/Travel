package com.Travel.biz.UserService.Service.Register;

import com.Travel.Core.User.VO.UserVO;
import com.Travel.biz.UserService.Dto.UserServiceDto;

public interface UserRegisterService {
    void addUser(UserVO user);
    void isDuplicateEmail(String email);
    void isDuplicateId(String userId);
    void sendRegisterMail(String email, String authNum);
}
