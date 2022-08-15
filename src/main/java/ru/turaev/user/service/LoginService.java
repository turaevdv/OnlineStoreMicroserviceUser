package ru.turaev.user.service;

import ru.turaev.user.dto.LoginViewModel;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    String authenticate(LoginViewModel model);
    boolean verifyToken(HttpServletRequest request);
}
