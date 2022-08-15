package ru.turaev.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.turaev.user.dto.LoginViewModel;
import ru.turaev.user.exception.JwtAuthenticationException;
import ru.turaev.user.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users/auth")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginViewModel model) {
        String token = loginService.authenticate(model);
        Map<Object, Object> response = new HashMap<>();
        response.put("login", model.getUsername());
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/token-verification")
    public boolean verifyToken(HttpServletRequest request) {
        try {
            return loginService.verifyToken(request);
        } catch (JwtAuthenticationException ex) {
            return false;
        }
    }
}
