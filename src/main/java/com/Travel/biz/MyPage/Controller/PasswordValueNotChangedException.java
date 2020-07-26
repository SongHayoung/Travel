package com.Travel.biz.MyPage.Controller;

public class PasswordValueNotChangedException extends RuntimeException {
    public PasswordValueNotChangedException() { super(); }

    public PasswordValueNotChangedException(String message) { super(message); }

    public PasswordValueNotChangedException(Throwable cause) { super(cause); }

    public PasswordValueNotChangedException(String message, Throwable cause) { super(message, cause); }
}