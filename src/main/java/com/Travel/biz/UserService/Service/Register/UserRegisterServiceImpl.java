package com.Travel.biz.UserService.Service.Register;

import com.Travel.Core.Annotations.TODO;
import com.Travel.Core.MailService.MailService;
import com.Travel.Core.User.Dao.DuplicateKeyException;
import com.Travel.Core.User.Dao.UserDao;
import com.Travel.Core.User.VO.UserVO;
import com.Travel.biz.UserService.Dto.UserServiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@TODO("유저 추가 에러처리 로직 필요")
@Service
@Transactional
public class UserRegisterServiceImpl implements UserRegisterService{
    @Autowired UserDao userDao;
    @Autowired MailService mailService;
    @Value("${mail.regMailSubject}") String regSubject;

    public void addUser(UserVO user) {
        userDao.isEmailExists(user.getEmail());
        userDao.isIdExists(user.getId());

        userDao.addUser(user);
    }

    @Transactional(readOnly = true)
    public void isDuplicateEmail(String email) {
        try {
            userDao.isEmailExists(email);
        }
        catch (DuplicateKeyException e) {
            throw new DuplicateUserInfoException(e);
        }
    }

    @Transactional(readOnly = true)
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
