package ru.practicum.shareit.common.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public static final long ID_1 = 1L;

    public static final String NAME_USER = "testUserName";

    public static final String EMAIL_USER = "testUserEMail@yandex.ru";

    public final User user1 = new User(ID_1, NAME_USER, EMAIL_USER);

    public final UserDto userDto = new UserDto(ID_1, NAME_USER, EMAIL_USER);

    @Test
    void toDtoTest() {
        UserDto userDto = userMapper.toDto(user1);
        assertTrue(user1.getId() == userDto.getId());
        assertTrue(user1.getName().equals(userDto.getName()));
        assertTrue(user1.getEmail().equals(userDto.getEmail()));
        assertNull(userMapper.toDto(null));
    }

    @Test
    void toDtoEntity() {
        User user = userMapper.toEntity(userDto);
        assertTrue(user.getId() == userDto.getId());
        assertTrue(user.getName().equals(userDto.getName()));
        assertTrue(user.getEmail().equals(userDto.getEmail()));
        assertNull(userMapper.toEntity(null));
    }

    @Test
    void toUpdate() {
        User user = userMapper.update(userDto, user1);
        assertTrue(user.getId() == userDto.getId());
        assertTrue(user.getName().equals(userDto.getName()));
        assertTrue(user.getEmail().equals(userDto.getEmail()));
        assertNull(userMapper.update(null, null));
    }
}
