package com.Travel.biz.UserService.Service.Register;

import com.Travel.biz.User.VO.UserVO;

public interface UserRegisterService {
    void addUser(UserVO user) throws DuplicateUserEmailException, DuplicateUserIDException;
    void sendRegisterMail(String email, String authNum);
}
