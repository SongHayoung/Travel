package com.Travel.biz.UserService.Service.Register;

import com.Travel.Core.MailService.MailService;
import com.Travel.Core.User.Dao.DuplicateKeyException;
import com.Travel.Core.User.Dao.UserDao;
import com.Travel.Core.User.VO.UserVO;
import com.Travel.biz.UserService.Controller.DuplicateUserIDException;
import com.Travel.biz.UserService.Dto.UserServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterServiceImpl implements UserRegisterService{
    @Autowired UserDao userDao;
    @Autowired MailService mailService;
    @Value("${mail.regMailSubject}") String regSubject;

    public void addUser(UserVO user) {
        userDao.isEmailExists(user.getEmail());
        userDao.isIdExists(user.getId());

        userDao.addUser(user);
    }

    public void isDuplicateEmail(String email) {
        try {
            userDao.isEmailExists(email);
        }
        catch (DuplicateKeyException e) {
            throw new DuplicateUserInfoException(e);
        }
    }

    public void isDuplicateId(UserServiceDto.Id user) {
        try {
            userDao.isIdExists(user.getId());
        }
        catch (DuplicateKeyException e) {
            throw new DuplicateUserInfoException(e);
        }
    }

    public void sendRegisterMail(String email, String authNum) {
        mailService.sendMail(email, regSubject, authNum);
    }
}
