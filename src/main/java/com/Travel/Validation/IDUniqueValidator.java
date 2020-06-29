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
public class IDUniqueValidator implements ConstraintValidator<IDUnique, String> {
    private final UserDao userDao;

    @Override
    public void initialize(IDUnique idUnique) { }

    @Override
    public boolean isValid(String id, ConstraintValidatorContext cxt) {
        return userDao.isIdExists(id);
    }
}
