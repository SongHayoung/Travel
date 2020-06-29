package com.Travel.Core.User.Dao;

public class DuplicateKeyException extends RuntimeException {
    public DuplicateKeyException() { super(); }

    public DuplicateKeyException(String message) { super(message); }

    public DuplicateKeyException(Throwable cause) { super(cause); }

    public DuplicateKeyException(String message, Throwable cause) { super(message, cause); }
}