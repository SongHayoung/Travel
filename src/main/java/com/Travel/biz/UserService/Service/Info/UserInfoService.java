package com.Travel.biz.UserService.Service.Info;

import com.Travel.biz.UserService.Dto.UserLoginDto;
import com.Travel.biz.UserService.Dto.UserIdDto;
import com.Travel.biz.UserService.Dto.UserNicknameDto;
import com.Travel.biz.UserService.Dto.UserPasswordDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserInfoService {
    boolean updateUserNickNameByID(UserNicknameDto user);
    boolean updateUserPasswordByID(UserPasswordDto user)
            throws PasswordValidationFailureException , PasswordValueNotChangedException;
    void deleteUserByID(UserIdDto user);
    boolean loginUser(UserLoginDto user) throws PasswordValidationFailureException;
}
