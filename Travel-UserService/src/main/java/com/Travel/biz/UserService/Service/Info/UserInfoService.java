package com.Travel.biz.UserService.Service.Info;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserInfoService {
    boolean updateUserNickNameByID(String id, String nickname);
    boolean updateUserPasswordByID(String id, String currentPass, String newPass)
            throws PasswordValidationFailureException , PasswordValueNotChangedException;
    void deleteUserByID(String id);
}
