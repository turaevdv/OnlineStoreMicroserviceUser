package ru.turaev.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.turaev.user.dto.UserDto;
import ru.turaev.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUserDtos();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable long id) {
        return userService.findUserDtoById(id);
    }

    @GetMapping("/any")
    public List<UserDto> getAllUsersOfAnyTypes() {
        return userService.findAllUserDtoOfAnyTypes();
    }

    @GetMapping("/any/{id}")
    public UserDto getUserOfAnyTypes(@PathVariable long id) {
        return userService.findUserDtoOfAnyTypes(id);
    }

    @PutMapping("/{id}")
    public void giveAdminRootToUser(@PathVariable long id) {
        userService.giveAdminRootToUser(id);
    }

    @PutMapping("/block/{id}")
    public void blockUser(@PathVariable long id) {
        userService.blockUser(id);
    }

    @PutMapping("/unblock/{id}")
    public void unblockUser(@PathVariable long id) {
        userService.unblockUser(id);
    }

    @DeleteMapping("/{id}")
    public UserDto deleteUser(@PathVariable long id) {
        return userService.deleteById(id);
    }

    @GetMapping("/place-order/{id}")
    public boolean canUserPlaceOrder(@PathVariable long id) {
        return userService.canUserPlaceOrder(id);
    }
}
