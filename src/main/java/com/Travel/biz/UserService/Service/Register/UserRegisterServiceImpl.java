package com.Travel.biz.UserService.Service.Register;

import com.Travel.Core.MailService.MailService;
import com.Travel.Core.User.Dao.UserDao;
import com.Travel.Core.User.VO.UserVO;
import com.Travel.biz.UserService.Dto.UserIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterServiceImpl implements UserRegisterService{
    @Autowired UserDao userDao;
    @Autowired MailService mailService;
    @Value("${mail.regMailSubject}") String regSubject;

    public void addUser(UserVO user) throws DuplicateUserEmailException, DuplicateUserIDException {
        if(userDao.isEmailExists(user.getEmail()) == false)
            throw new DuplicateUserEmailException();
        if(userDao.isIdExists(user.getId()) == false)
            throw new DuplicateUserIDException();

        userDao.addUser(user);
    }

    public boolean isDuplicateEmail(String email) throws DuplicateUserEmailException {
        if(userDao.isEmailExists(email) == false)
            throw new DuplicateUserEmailException();

        return true;
    }
    public boolean isDuplicateId(UserIdDto user) throws DuplicateUserIDException {
        if(userDao.isIdExists(user.getId()) == true)
            throw new DuplicateUserIDException();

        return true;
    }

    public void sendRegisterMail(String email, String authNum) {
        mailService.sendMail(email, regSubject, authNum);
    }
}
