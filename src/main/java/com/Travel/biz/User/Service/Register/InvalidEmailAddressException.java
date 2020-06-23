package com.Travel.biz.User.Service.Register;

public class InvalidEmailAddressException extends RuntimeException {
    public InvalidEmailAddressException() { super(); }

    public InvalidEmailAddressException(String message) { super(message); }

    public InvalidEmailAddressException(Throwable cause) { super(cause); }

    public InvalidEmailAddressException(String message, Throwable cause) { super(message, cause); }
}
