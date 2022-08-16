package ru.turaev.user.service;

import ru.turaev.user.dto.UserDto;
import ru.turaev.user.model.User;

import java.util.List;

public interface UserService {
    User findUserById(long id);

    UserDto findUserDtoById(long id);

    User save(User user);

    User findUserByUsername(String username);

    List<UserDto> findAllUserDtos();

    UserDto findUserDtoOfAnyTypes(long id);

    List<UserDto> findAllUserDtoOfAnyTypes();

    UserDto deleteById(long id);

    boolean isUsernameExist(String username);

    void blockUser(long id);

    void unblockUser(long id);

    void giveAdminRootToUser(long id);

    boolean canUserPlaceOrder(long id);
}
