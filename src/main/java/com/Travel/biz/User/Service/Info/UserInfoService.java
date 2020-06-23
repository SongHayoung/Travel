package com.Travel.biz.User.Service.Info;

import com.Travel.biz.User.VO.UserVO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserInfoService {
    void updateUserNickNameByID(String id, String nickname);
    void updateUserPasswordByID(String id, String currentPass, String newPass);
    void deleteUserByID(String id);
    @Transactional(readOnly = true)
    void validateUserPass(String id, String pass);

    @Transactional(readOnly = true)
    UserVO getUserByID(String id);
}
