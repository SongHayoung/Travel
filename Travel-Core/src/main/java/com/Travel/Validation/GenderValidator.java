package com.Travel.Validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class GenderValidator implements ConstraintValidator<Gender, String> {
    @Override
    public void initialize(Gender gender) { }

    @Override
    public boolean isValid(String gender, ConstraintValidatorContext cxt) {
        return gender.equals("M") || gender.equals("F");
    }
}
