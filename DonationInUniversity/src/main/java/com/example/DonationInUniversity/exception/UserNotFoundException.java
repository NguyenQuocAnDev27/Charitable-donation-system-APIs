package com.example.DonationInUniversity.exception;

public class UserNotFoundException extends MyException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
