package com.Travel.biz.User.Service.Info;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserInfoService {
    boolean updateUserNickNameByID(String id, String nickname);
    boolean updateUserPasswordByID(String id, String currentPass, String newPass)
            throws PasswordValidationFailureException , PasswordValueNotChangedException;
    void deleteUserByID(String id);
    boolean loginUser(String id, String pass) throws PasswordValidationFailureException;
}
