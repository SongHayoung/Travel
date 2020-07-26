package com.Travel.biz.MyPage.Service.Info;

import com.Travel.Core.User.VO.UserVO;
import com.Travel.biz.MyPage.Dto.UserServiceDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserInfoService {
    boolean updateUserNickNameByID(String userId, String nickName);
    boolean updateUserPasswordByID(String userId, UserServiceDto.ChangePass user)
            throws IncorrectException, NotChangedException;
    void deleteUserByID(String userId);
    UserVO loginUser(UserServiceDto.Login user) throws IncorrectException;
}
