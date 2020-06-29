package com.Travel.biz.UserService.Service.Info;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException() { super(); }

    public NoSuchUserException(String message) { super(message); }

    public NoSuchUserException(Throwable cause) { super(cause); }

    public NoSuchUserException(String message, Throwable cause) { super(message, cause); }
}
