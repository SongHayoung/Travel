package com.Travel.biz.MyPage.Service.Info;

public class IncorrectException extends RuntimeException {
    public IncorrectException() { super(); }

    public IncorrectException(String message) { super(message); }

    public IncorrectException(Throwable cause) { super(cause); }

    public IncorrectException(String message, Throwable cause) { super(message, cause); }
}