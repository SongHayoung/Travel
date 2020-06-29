package com.Travel.biz.UserService.Service.Info;

import com.Travel.Core.User.Dao.UserDao;
import com.Travel.Core.User.VO.UserVO;
import com.Travel.biz.UserService.Dto.UserServiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired UserDao userDao;
    private Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);
    public boolean updateUserNickNameByID(UserServiceDto.ChangeNickname userNewNickname) {
        UserVO targetUser = userDao.getUser(userNewNickname.getId());
        targetUser.setNickname(userNewNickname.getNickname());
        userDao.updateUser(targetUser);
        return true;
    }

    public boolean updateUserPasswordByID(UserServiceDto.ChangePass userNewPassword)
            throws IncorrectException, NotChangedException {
        UserVO targetUser = userDao.getUser(userNewPassword.getId());
        //현재 비밀번호가 옳지 않을 때
        if(targetUser.getPass().equals(userNewPassword.getCurrentPass()) == false)
            throw  new IncorrectException();
        //현재 비밀번호가 새로운 비밀번호랑와 일치할 때
        if(userNewPassword.getCurrentPass().equals(userNewPassword.getNewPass()) == true)
            throw new NotChangedException();
        targetUser.setPass(userNewPassword.getNewPass());
        userDao.updateUser(targetUser);

        return true;
    }

    public void deleteUserByID(UserServiceDto.Id user) {
        UserVO targetUser = userDao.getUser(user.getId());
        userDao.deleteUser(targetUser);
    }

    public boolean loginUser(UserServiceDto.Login loginAccount) throws IncorrectException {
        UserVO targetUser = userDao.getUser(loginAccount.getId());
        if(targetUser.getPass().equals(loginAccount.getPass()) == false)
            throw new IncorrectException();

        return true;
    }
}
