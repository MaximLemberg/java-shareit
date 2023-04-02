package ru.practicum.shareit.user.mapper;


import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@Component
public class UserMapper {

    public User toEntity(UserDto userDto, boolean checkEmail) {
        if (!checkEmail) {
            return new User(0L,
                    userDto.getName(),
                    userDto.getEmail()
            );
        } else return null;
    }

    public User toEntity(UserDto userDto, User user) {
        if (userDto.getName() == null) {
            user.setName(user.getName());
        } else user.setName(userDto.getName());
        if (userDto.getEmail() == null)  {
            user.setEmail(user.getEmail());
        } else user.setEmail(userDto.getEmail());
        return user;
    }

    public UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

}
