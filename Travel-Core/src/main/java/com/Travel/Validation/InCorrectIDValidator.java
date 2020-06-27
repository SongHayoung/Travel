package com.Travel.Validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class InCorrectIDValidator implements ConstraintValidator<InCorrectID, String> {
    private static Pattern p = null;
    private static Matcher m = null;

    //첫글자는 무조건 영어 나머지 글자는 영어 / 숫자 / _ 허용 / 6 ~ 12 글자 제한
    private static final String ID_PATTERN = "^[a-zA-Z]{1}[a-zA-Z0-9_]{6,12}+$";

    @Override
    public void initialize(InCorrectID id) { }

    @Override
    public boolean isValid(String id, ConstraintValidatorContext cxt) {
        p = Pattern.compile(ID_PATTERN);
        m = p.matcher(id);
        return m.find();
    }
}
