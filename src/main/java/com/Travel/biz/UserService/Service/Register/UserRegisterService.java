package com.Travel.biz.UserService.Service.Register;

import com.Travel.Core.User.VO.UserVO;
import com.Travel.biz.UserService.Dto.UserIdDto;

public interface UserRegisterService {
    void addUser(UserVO user) throws DuplicateUserEmailException, DuplicateUserIDException;
    boolean isDuplicateEmail(String email) throws DuplicateUserEmailException;
    boolean isDuplicateId(UserIdDto user) throws DuplicateUserIDException;
    void sendRegisterMail(String email, String authNum);
}
