package com.Travel.biz.MyPage.Service.Register;

public class DuplicateUserInfoException extends RuntimeException {
    public DuplicateUserInfoException() { super(); }

    public DuplicateUserInfoException(String message) { super(message); }

    public DuplicateUserInfoException(Throwable cause) { super(cause); }

    public DuplicateUserInfoException(String message, Throwable cause) { super(message, cause); }
}
