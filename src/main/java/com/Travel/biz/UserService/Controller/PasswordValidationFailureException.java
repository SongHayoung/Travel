package com.Travel.biz.UserService.Controller;

public class PasswordValidationFailureException extends RuntimeException {
    public PasswordValidationFailureException() { super(); }

    public PasswordValidationFailureException(String message) { super(message); }

    public PasswordValidationFailureException(Throwable cause) { super(cause); }

    public PasswordValidationFailureException(String message, Throwable cause) { super(message, cause); }
}
