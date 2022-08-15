package ru.turaev.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    @Getter
    private final HttpStatus status;

    public JwtAuthenticationException(String s, HttpStatus httpStatus) {
        super(s);
        this.status = httpStatus;
    }
}
