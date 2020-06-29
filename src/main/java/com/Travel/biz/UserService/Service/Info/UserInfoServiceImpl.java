package com.Travel.biz.UserService.Service.Info;

import com.Travel.Core.User.Dao.UserDao;
import com.Travel.Core.User.VO.UserVO;
import com.Travel.biz.UserService.Dto.UserLoginDto;
import com.Travel.biz.UserService.Dto.UserIdDto;
import com.Travel.biz.UserService.Dto.UserNicknameDto;
import com.Travel.biz.UserService.Dto.UserPasswordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired UserDao userDao;

    public boolean updateUserNickNameByID(UserNicknameDto userNewNickname) {
        UserVO targetUser = userDao.getUser(userNewNickname.getId());
        targetUser.setNickname(userNewNickname.getNickname());
        userDao.updateUser(targetUser);
        return true;
    }

    public boolean updateUserPasswordByID(UserPasswordDto userNewPassword)
            throws PasswordValidationFailureException , PasswordValueNotChangedException {
        UserVO targetUser = userDao.getUser(userNewPassword.getId());
        //현재 비밀번호가 옳지 않을 때
        if(targetUser.getPass().equals(userNewPassword.getCurrentPass()) == false)
            throw  new PasswordValidationFailureException();
        //현재 비밀번호가 새로운 비밀번호랑와 일치할 때
        if(userNewPassword.getCurrentPass().equals(userNewPassword.getNewPass()) == true)
            throw new PasswordValueNotChangedException();
        targetUser.setPass(userNewPassword.getNewPass());
        userDao.updateUser(targetUser);

        return true;
    }

    public void deleteUserByID(UserIdDto user) {
        UserVO targetUser = userDao.getUser(user.getId());
        userDao.deleteUser(targetUser);
    }

    public boolean loginUser(UserLoginDto loginAccount) throws PasswordValidationFailureException {
        UserVO targetUser = userDao.getUser(loginAccount.getId());

        if(targetUser.getPass().equals(loginAccount.getPass()) == false)
            throw new PasswordValidationFailureException();

        return true;
    }
}
