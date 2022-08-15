package ru.turaev.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public UserNotFoundException() {
        super("User not found", HttpStatus.NOT_FOUND);
    }
}
