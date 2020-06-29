package com.Travel.Core.User.Dao;

public class NoValueException extends RuntimeException {
    public NoValueException() { super(); }

    public NoValueException(String message) { super(message); }

    public NoValueException(Throwable cause) { super(cause); }

    public NoValueException(String message, Throwable cause) { super(message, cause); }
}
