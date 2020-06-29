package com.Travel.Validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class InCorrectNameValidator implements ConstraintValidator<InCorrectName, String> {
    @Override
    public void initialize(InCorrectName constraintAnnotation) { }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return 2 <= name.length() && name.length() <= 5;
    }
}
