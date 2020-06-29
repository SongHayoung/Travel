package com.Travel.Validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = InCorrectPasswordValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InCorrectPassword {
    String message() default "InCorrectPassword.message";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
