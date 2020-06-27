package com.Travel.biz.UserService.Service.Register;

import com.Travel.biz.Mail.Service.MailService;
import com.Travel.biz.User.Dao.UserDao;
import com.Travel.biz.User.VO.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterServiceImpl implements UserRegisterService{
    private final UserDao userDao;
    private final MailService mailService;
    @Value("${mail.regMailSubject}") String regSubject;

    public void addUser(UserVO user) throws DuplicateUserEmailException, DuplicateUserIDException {
        if(userDao.isEmailExists(user.getEmail()) == false)
            throw new DuplicateUserEmailException();
        if(userDao.isIdExists(user.getId()) == false)
            throw new DuplicateUserIDException();

        userDao.addUser(user);
    }

    public void sendRegisterMail(String email, String authNum) {
        mailService.sendMail(email, regSubject, authNum);
    }
}
