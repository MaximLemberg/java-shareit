package ru.practicum.shareit.user.model.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDtoTest {

    public static final long ID_1 = 1L;

    public static final String NAME_USER_DTO = "testUserDtoName";

    public static final String EMAIL_USER_DTO = "testUserDtoEMail@yandex.ru";

    public final UserDto userDto = new UserDto(ID_1, NAME_USER_DTO, EMAIL_USER_DTO);


    @Test
    void testGetter() {
        UserDto newUserDto = new UserDto(userDto.getId(), userDto.getName(), userDto.getEmail());
        assertTrue(userDto.getId() == newUserDto.getId());
        assertTrue(userDto.getName().equals(newUserDto.getName()));
        assertTrue(userDto.getEmail().equals(newUserDto.getEmail()));
    }

}
