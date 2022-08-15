package ru.turaev.user.exception.exceptionmodel;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;
import ru.turaev.user.dto.UserDto;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
public class IllegalUserExceptionModel extends BaseExceptionModel {
    private final UserDto userDto;

    public IllegalUserExceptionModel(String message,
                                     HttpStatus status,
                                     LocalDateTime localDateTime,
                                     UserDto userDto) {
        super(message, status, localDateTime);
        this.userDto = userDto;
    }
}
