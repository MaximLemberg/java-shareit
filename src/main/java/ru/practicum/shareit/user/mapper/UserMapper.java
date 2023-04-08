package ru.practicum.shareit.user.mapper;

import ru.practicum.shareit.common.mapper.Mapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

@org.mapstruct.Mapper(componentModel = "spring")
public interface UserMapper extends Mapper<User, UserDto> {


}
