package ru.turaev.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.turaev.user.dto.LoginViewModel;
import ru.turaev.user.dto.UserDto;
import ru.turaev.user.model.User;
import ru.turaev.user.service.LoginService;
import ru.turaev.user.service.RegistrationService;

@RestController
@RequestMapping("api/v1/users/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    private final LoginService loginService;

    @GetMapping
    public boolean isUsernameUnused(@RequestParam("username") String username) {
        return registrationService.isUsernameUnused(username);
    }

    @PostMapping
    public UserDto registerUser(@RequestBody LoginViewModel loginViewModel) {
         UserDto userDto = registrationService.registerUser(loginViewModel);
         loginService.authenticate(loginViewModel);
         return userDto;
    }

    @PostMapping("/any")
    public UserDto registerUserOfAnyType(@RequestBody User user) {
        return registrationService.registerUserOfAnyType(user);
    }
}