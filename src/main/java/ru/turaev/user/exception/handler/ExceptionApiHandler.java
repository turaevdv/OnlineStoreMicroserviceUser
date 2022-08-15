package ru.turaev.user.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.turaev.user.exception.*;
import ru.turaev.user.exception.exceptionmodel.BaseExceptionModel;
import ru.turaev.user.exception.exceptionmodel.IllegalUserExceptionModel;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler({UserNotFoundException.class, UserAlreadyExistException.class, CredentialException.class, BaseException.class})
    public ResponseEntity<BaseExceptionModel> handleUserNotFoundException(BaseException ex) {
        HttpStatus httpStatus = ex.getStatus();
        BaseExceptionModel exceptionModel = BaseExceptionModel.builder()
                .message(ex.getMessage())
                .status(httpStatus)
                .localDateTime(ex.getLocalDateTime())
                .build();
        return new ResponseEntity<>(exceptionModel, httpStatus);
    }

    @ExceptionHandler(IllegalUserException.class)
    public ResponseEntity<IllegalUserExceptionModel> handleIllegalUserException(IllegalUserException ex) {
        HttpStatus httpStatus = ex.getStatus();
        IllegalUserExceptionModel exceptionModel = IllegalUserExceptionModel.builder()
                .userDto(ex.getUserDto())
                .message(ex.getMessage())
                .status(httpStatus)
                .localDateTime(ex.getLocalDateTime())
                .build();
        return new ResponseEntity<>(exceptionModel, httpStatus);
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<BaseExceptionModel> handleJwtAuthenticationException(JwtAuthenticationException ex) {
        HttpStatus httpStatus = ex.getStatus();
        BaseExceptionModel exceptionModel = BaseExceptionModel.builder()
                .message(ex.getMessage())
                .status(httpStatus)
                .localDateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(exceptionModel, httpStatus);
    }
}