package ru.turaev.user.exception;

import org.springframework.http.HttpStatus;

public class CredentialException extends BaseException {
    public CredentialException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }

    public CredentialException() {
        this("Invalid login/password combination");
    }
}
