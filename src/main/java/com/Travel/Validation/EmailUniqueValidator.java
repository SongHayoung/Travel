package com.Travel.Validation;

import com.Travel.Core.User.Dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
//UserDao DI
//순환 참조 방지
@RequiredArgsConstructor
public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {
    private final UserDao userDao;

    @Override
    public void initialize(EmailUnique emailUnique) { }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext cxt) {
        return userDao.isEmailExists(email);
    }
}