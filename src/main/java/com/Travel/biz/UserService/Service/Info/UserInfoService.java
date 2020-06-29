package com.Travel.biz.UserService.Service.Info;

import com.Travel.biz.UserService.Controller.PasswordValueNotChangedException;
import com.Travel.biz.UserService.Dto.UserServiceDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserInfoService {
    boolean updateUserNickNameByID(UserServiceDto.ChangeNickname user);
    boolean updateUserPasswordByID(UserServiceDto.ChangePass user)
            throws IncorrectException, NotChangedException;
    void deleteUserByID(UserServiceDto.Id user);
    boolean loginUser(UserServiceDto.Login user) throws IncorrectException;
}
