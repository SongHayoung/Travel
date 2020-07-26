package com.Travel.biz.MyPage.Controller;

public class DuplicateUserIDException extends RuntimeException {
    public DuplicateUserIDException() { super(); }

    public DuplicateUserIDException(String message) { super(message); }

    public DuplicateUserIDException(Throwable cause) { super(cause); }

    public DuplicateUserIDException(String message, Throwable cause) { super(message, cause); }
}
