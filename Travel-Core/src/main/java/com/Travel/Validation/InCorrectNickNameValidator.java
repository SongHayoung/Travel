package com.Travel.Validation;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Configuration
@RequiredArgsConstructor
public class InCorrectNickNameValidator implements ConstraintValidator<InCorrectNickName, String> {
    @Override
    public void initialize(InCorrectNickName constraintAnnotation) { }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return 2 <= name.length() && name.length() <= 15;
    }
}