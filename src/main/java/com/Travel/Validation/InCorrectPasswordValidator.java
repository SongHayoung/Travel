package com.Travel.Validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InCorrectPasswordValidator implements ConstraintValidator<InCorrectPassword, String> {
    private static Pattern p = null;
    private static Matcher m = null;
    //영어 / 숫자 / 특수문자 허용 8 ~ 15자 제한
    private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9!@#$%^&*()]{8,15}+$";

    @Override
    public void initialize(InCorrectPassword constraintAnnotation) { }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        p = Pattern.compile(PASSWORD_PATTERN);
        m = p.matcher(password);
        return m.find();
    }
}
