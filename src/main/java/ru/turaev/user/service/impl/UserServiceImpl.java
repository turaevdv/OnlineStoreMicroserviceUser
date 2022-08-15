package ru.turaev.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.turaev.user.dto.UserDto;
import ru.turaev.user.enums.Role;
import ru.turaev.user.exception.IllegalUserException;
import ru.turaev.user.exception.UserAlreadyExistException;
import ru.turaev.user.exception.UserNotFoundException;
import ru.turaev.user.mapper.UserMapper;
import ru.turaev.user.model.User;
import ru.turaev.user.repository.UserRepository;
import ru.turaev.user.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User findUserById(long id) throws UserNotFoundException {
        log.info("Try to find user with id = {}", id);
        User user = userRepository.findByIdAndRoleIs(id, Role.USER)
                .orElseThrow(() -> new UserNotFoundException("The user with id = " + id + " was not found"));
        log.info("The user with id = {} was found", id);
        return user;
    }

    @Override
    public UserDto findUserDtoById(long id) throws UserNotFoundException {
        return userMapper.toDto(findUserById(id));
    }

    @Override
    public User findUserByUsername(String username) throws UserNotFoundException {
        log.info("Try to find user with username = {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("The user with username = " + username + " was not found"));
        log.info("The user with username = {} was found", username);
        return user;
    }

    @Override
    public List<UserDto> findAllUserDtos() {
        return userRepository.findAllByRoleIs(Role.USER)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findUserDtoOfAnyTypes(long id) throws UserNotFoundException {
        log.info("Try to find any user with id = {}", id);
        UserDto userDto = userMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("The user with id = " + id + " was not found")));
        log.info("The user with id = {} was found", id);
        return userDto;
    }

    @Override
    public List<UserDto> findAllUserDtoOfAnyTypes() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public User save(User user) throws UserAlreadyExistException {
        log.info("Try to save user with username = {}", user.getUsername());
        if (isUsernameExist(user.getUsername())) {
            throw new UserAlreadyExistException("The user with username = " + user.getUsername() + " is already exist");
        }
        userRepository.save(user);
        log.info("The user with the username = {} was saved with an id = {}", user.getUsername(), user.getId());
        return user;
    }

    @Transactional
    @Override
    public UserDto deleteById(long id) throws UserNotFoundException, IllegalUserException {
        log.info("Try to delete user with id = {}", id);
        User user = findUserById(id);
        user.setActive(false);
        log.info("The user with id = {} has been deleted", id);
        return userMapper.toDto(user);
    }

    @Override
    public boolean isUsernameExist(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    @Override
    public void blockUser(long id) {
        log.info("Try to block user with id = {}", id);
        User user = findUserById(id);
        if (!user.isNonLocked()) {
            throw new IllegalUserException("The user with id = " + id + " is already blocked", HttpStatus.FORBIDDEN, userMapper.toDto(user));
        }
        user.setNonLocked(false);
        log.info("The user with id = {} has been blocked", id);
    }

    @Transactional
    @Override
    public void unblockUser(long id) {
        log.info("Try to unblock user with id = {}", id);
        User user = findUserById(id);
        if (user.isNonLocked()) {
            throw new IllegalUserException("The user with id = " + id + " is already unblocked", HttpStatus.FORBIDDEN, userMapper.toDto(user));
        }
        user.setNonLocked(true);
        log.info("The user with id = {} has been unblocked", id);
    }

    @Transactional
    @Override
    public void giveAdminRootToUser(long id) throws UserNotFoundException, IllegalUserException {
        log.info("Try to give admin root to user with id = {}", id);
        User user = findUserById(id);
        if (user.getRole().equals(Role.ADMIN)) {
            throw new IllegalUserException("This user with id = " + id + " is already an administrator", HttpStatus.BAD_REQUEST, userMapper.toDto(user));
        }
        user.setRole(Role.ADMIN);
        log.info("The user with id = {} has received admin root", id);
    }
}
