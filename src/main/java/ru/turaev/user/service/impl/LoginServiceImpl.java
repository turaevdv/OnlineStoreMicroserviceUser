package ru.turaev.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import ru.turaev.user.dto.LoginViewModel;
import ru.turaev.user.exception.CredentialException;
import ru.turaev.user.exception.IllegalUserException;
import ru.turaev.user.mapper.UserMapper;
import ru.turaev.user.model.User;
import ru.turaev.user.securityutil.JwtTokenProvider;
import ru.turaev.user.service.LoginService;
import ru.turaev.user.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    @Override
    public String authenticate(LoginViewModel model) {
        log.info("Try to authenticate user with username = {}", model.getUsername());
        User user = userService.findUserByUsername(model.getUsername());

        if (!user.isNonLocked()) {
            throw new IllegalUserException("The user with username = " + user.getUsername() + " is blocked", HttpStatus.FORBIDDEN, userMapper.toDto(user));
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(model.getUsername(), model.getPassword()));
        } catch (AuthenticationException ex) {
            throw new CredentialException("Password is incorrect");
        }

        return jwtTokenProvider.createJwtToken(user.getUsername(), user.getRole());
    }

    @Override
    public boolean verifyToken(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token.isBlank()) {
            return false;
        }
        return jwtTokenProvider.validateToken(token);
    }
}
