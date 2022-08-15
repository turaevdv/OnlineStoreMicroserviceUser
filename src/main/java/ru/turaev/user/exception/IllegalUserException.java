package ru.turaev.user.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import ru.turaev.user.dto.UserDto;

public class IllegalUserException extends BaseException {
    @Getter
    @Setter
    private UserDto userDto;

    public IllegalUserException(String message, HttpStatus httpStatus, UserDto userDto) {
        super(message, httpStatus);
        this.userDto = userDto;
    }
}
