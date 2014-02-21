package com.ep.hippyjava.networking.exceptions;

public class LoginException extends Exception {

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(String message) {
        super(message);
    }
}
