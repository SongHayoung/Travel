package com.Travel.biz.User.Service.Register;

import com.Travel.biz.Mail.Service.MailService;
import com.Travel.biz.User.Dao.UserDao;
import com.Travel.biz.User.VO.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserRegisterServiceImpl implements UserRegisterService{
    @Autowired UserDao userDao;
    @Autowired  MailService mailService;
    private static final int DUPLICATE_USER_ID = 1;
    private static final int DUPLICATE_USER_EMAIL = 1;

    public void addUser(UserVO user) { userDao.addUser(user); }

    public void confirmUserId(String id) {
        validUserId(id);
        duplicateUserId(id);
    }

    private void duplicateUserId(String id) throws DuplicateUserIdException {
        int value = userDao.getIdExists(id);
        if(value == DUPLICATE_USER_ID)
            throw new DuplicateUserIdException("이미 존재하는 아이디 입니다");
    }

    //사용자 아이디 정규식
    //시작은 영문으로만 가능하며 , '_'를 제외한 특수문자 안되며 영문, 숫자, '_'으로만 이루어진 5 ~ 12자 이하 아이디
    private void validUserId(String id) throws InvalidUserIdException {
        Pattern ptr = Pattern.compile("^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$");
        if(!ptr.matcher(id).matches())
            throw new InvalidUserIdException("올바른 아이디가 아닙니다");
    }

    public void confirmUserEmail(String email) {
        validUserEmail(email);
        duplicateUserEmail(email);
    }

    private void duplicateUserEmail(String email) throws DuplicateUserEmailException {
        int value = userDao.getEmailExists(email);
        if(value == DUPLICATE_USER_EMAIL)
            throw new DuplicateUserEmailException("이미 존재하는 이메일 입니다");

        mailService.sendRegisterMail(email);
    }

    //사용자 이메일 정규식
    //ID부 영어 대.소문자 숫자 . _ 특수문자 가능
    //HOST부 영어 대.소문자 숫자만 가능
    private void validUserEmail(String email) throws InvalidEmailAddressException {
       Pattern ptr = Pattern.compile("^[a-zA-Z0-9._]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,6}$");
        if(!ptr.matcher(email).matches())
            throw new InvalidEmailAddressException("올바른 이메일 주소가 아닙니다");
    }
}
