package com.Travel.biz.User.Service.Register;

public class DuplicateUserIdException extends RuntimeException {
    public DuplicateUserIdException() { super(); }

    public DuplicateUserIdException(String message) { super(message); }

    public DuplicateUserIdException(Throwable cause) { super(cause); }

    public DuplicateUserIdException(String message, Throwable cause) { super(message, cause); }
}
