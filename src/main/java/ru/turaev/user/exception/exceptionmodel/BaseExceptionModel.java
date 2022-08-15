package ru.turaev.user.exception.exceptionmodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class BaseExceptionModel {
    private String message;
    private HttpStatus status;
    private LocalDateTime localDateTime;
}
