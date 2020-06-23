package com.Travel.biz.User.Service.Info;

import com.Travel.biz.User.Dao.UserDao;
import com.Travel.biz.User.VO.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired UserDao userDao;

    public void updateUserNickNameByID(String id, String nickname) {
        UserVO targetUser = userDao.getUser(id);
        targetUser.setNickname(nickname);
        updateUser(targetUser);
    }

    public void updateUserPasswordByID(String id, String currentPass, String newPass)
            throws PasswordValidationFailureException , PasswordValueNotChangedException {
        UserVO targetUser = userDao.getUser(id);
        validateUserPass(id, currentPass);

        if(currentPass.equals(newPass))
            throw new PasswordValueNotChangedException(targetUser.getId() + "의 새로운 비밀번호가 이전 비밀번호와 일치합니다");

        targetUser.setPass(newPass);
        updateUser(targetUser);
    }

    public void deleteUserByID(String id) {
        UserVO targetUser = getUserByID(id);
        userDao.deleteUser(targetUser);
    }

    public UserVO getUserByID(String id) {
        return userDao.getUser(id);
    }

    public void validateUserPass(String id, String pass) throws PasswordValidationFailureException{
        UserVO targetUser = getUserByID(id);
        if(! targetUser.getPass().equals(pass))
            throw new PasswordValidationFailureException(targetUser.getId() + "의 비밀번호가 일치하지 않습니다");
    }

    private void updateUser(UserVO user) {
        System.out.println(user.getId() + user.getPass());
        userDao.updateUser(user);
    }

}
