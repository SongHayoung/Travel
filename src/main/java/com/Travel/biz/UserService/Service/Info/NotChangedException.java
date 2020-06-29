package com.Travel.biz.UserService.Service.Info;

public class NotChangedException extends RuntimeException {
    public NotChangedException() { super(); }

    public NotChangedException(String message) { super(message); }

    public NotChangedException(Throwable cause) { super(cause); }

    public NotChangedException(String message, Throwable cause) { super(message, cause); }
}
