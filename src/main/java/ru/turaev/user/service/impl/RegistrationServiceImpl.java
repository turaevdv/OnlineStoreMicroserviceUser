package ru.turaev.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.turaev.user.dto.LoginViewModel;
import ru.turaev.user.dto.UserDto;
import ru.turaev.user.exception.BaseException;
import ru.turaev.user.exception.UserAlreadyExistException;
import ru.turaev.user.mapper.UserMapper;
import ru.turaev.user.model.User;
import ru.turaev.user.service.RegistrationService;
import ru.turaev.user.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDto registerUser(LoginViewModel loginViewModel) {
        log.info("Try to register user with username = {}", loginViewModel.getUsername());
        User user = userMapper.fromLoginViewModel(loginViewModel);
        return registerUserOfAnyType(user);
    }

    @Transactional
    @Override
    public UserDto registerUserOfAnyType(User user) {
        if (!isUsernameUnused(user.getUsername())) {
            throw new UserAlreadyExistException("The user with username = " + user.getUsername() + " already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public boolean isUsernameUnused(String username) {
        if (username.isBlank()) {
            throw new BaseException("The request parameter is empty", HttpStatus.BAD_REQUEST);
        }
        return !userService.isUsernameExist(username);
    }
}
