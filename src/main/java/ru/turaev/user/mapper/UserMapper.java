package ru.turaev.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.turaev.user.dto.LoginViewModel;
import ru.turaev.user.dto.UserDto;
import ru.turaev.user.model.User;

@Mapper(componentModel = "spring", imports = ru.turaev.user.enums.Role.class)
public interface UserMapper {
    UserDto toDto(User user);

    @Mapping(target = "role", expression = "java(Role.USER)")
    @Mapping(target = "active", expression = "java(true)")
    @Mapping(target = "nonLocked", expression = "java(true)")
    User fromLoginViewModel(LoginViewModel model);
}