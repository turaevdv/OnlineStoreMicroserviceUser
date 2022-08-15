package ru.turaev.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {
    USERS_WRITE("users.write"),
    USERS_READ("users.read"),
    USERS_UPDATE("users.update"),
    USERS_DELETE("users.delete");

    private final String permission;
}