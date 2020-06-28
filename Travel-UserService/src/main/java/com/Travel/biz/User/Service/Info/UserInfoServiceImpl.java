package com.Travel.biz.User.Service.Info;

import com.Travel.biz.User.Dao.UserDao;
import com.Travel.biz.User.VO.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired UserDao userDao;

    public boolean updateUserNickNameByID(String id, String nickname) {
        UserVO targetUser = userDao.getUser(id);
        targetUser.setNickname(nickname);
        userDao.updateUser(targetUser);
        return true;
    }

    public boolean updateUserPasswordByID(String id, String currentPass, String newPass)
            throws PasswordValidationFailureException , PasswordValueNotChangedException {
        UserVO targetUser = userDao.getUser(id);
        //현재 비밀번호가 옳지 않을 때
        if(targetUser.getPass().equals(currentPass) == false)
            throw  new PasswordValidationFailureException();
        //현재 비밀번호가 새로운 비밀번호랑와 일치할 때
        if(currentPass.equals(newPass) == true)
            throw new PasswordValueNotChangedException();
        targetUser.setPass(newPass);
        userDao.updateUser(targetUser);

        return true;
    }

    public void deleteUserByID(String id) {
        UserVO targetUser = userDao.getUser(id);
        userDao.deleteUser(targetUser);
    }

    public boolean loginUser(String id, String pass) throws PasswordValidationFailureException {
        UserVO targetUser = userDao.getUser(id);

        if(targetUser.getPass().equals(pass) == false)
            throw new PasswordValidationFailureException();

        return true;
    }
}
