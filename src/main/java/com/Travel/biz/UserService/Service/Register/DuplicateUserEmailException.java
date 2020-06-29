package com.Travel.biz.UserService.Service.Register;

public class DuplicateUserEmailException extends RuntimeException {
    public DuplicateUserEmailException() { super(); }

    public DuplicateUserEmailException(String message) { super(message); }

    public DuplicateUserEmailException(Throwable cause) { super(cause); }

    public DuplicateUserEmailException(String message, Throwable cause) { super(message, cause); }
}