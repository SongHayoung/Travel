package com.Travel.Core.Jwt;

public class InvalidUserRequestTokenException extends RuntimeException {
    public InvalidUserRequestTokenException() {
        super();
    }

    public InvalidUserRequestTokenException(String message) {
        super(message);
    }

    public InvalidUserRequestTokenException(Throwable cause) {
        super(cause);
    }

    public InvalidUserRequestTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
