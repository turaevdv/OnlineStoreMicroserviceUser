package ru.turaev.user.service;

import ru.turaev.user.dto.LoginViewModel;
import ru.turaev.user.dto.UserDto;
import ru.turaev.user.model.User;

public interface RegistrationService {
    UserDto registerUser(LoginViewModel loginViewModel);
    UserDto registerUserOfAnyType(User user);
    boolean isUsernameUnused(String username);
}
