package ru.turaev.user.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends BaseException {

    public UserAlreadyExistException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public UserAlreadyExistException() {
        super("User already exist", HttpStatus.BAD_REQUEST);
    }
}
