package com.Travel.biz.User.Service.Register;

public class InvalidUserIdException extends RuntimeException {
    public InvalidUserIdException() { super(); }

    public InvalidUserIdException(String message) { super(message); }

    public InvalidUserIdException(Throwable cause) { super(cause); }

    public InvalidUserIdException(String message, Throwable cause) { super(message, cause); }
}
