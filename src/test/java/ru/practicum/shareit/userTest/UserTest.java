package ru.practicum.shareit.userTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.service.UserServiceImpl;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @Mock
    private UserRepository userRepository;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private UserServiceImpl userService;
    private User user1;
    private UserDto userDto1;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(
                userRepository,
                userMapper
        );

        user1 = makeUser(1L);
        userDto1 = makeUserDto(1L);
    }

    private UserDto makeUserDto(long id) {
        return new UserDto(
                id,
                "name_" + id,
                "email_" + id + "@emal.ru"
        );
    }

    User makeUser(Long id) {
        return new User(
                id,
                "name_" + id,
                "email_" + id + "@emal.ru"
        );
    }

    @Test
    void addUserTest() {
        when(userRepository.save(any(User.class))).thenAnswer(returnsFirstArg());

        UserDto added = userService.add(userDto1);

        assertEquals(userMapper.toDto(user1), added);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void findAllUserTest() {
        User user2 = makeUser(2L);
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserDto> found = userService.findAll();

        assertNotNull(found);
        assertEquals(2, found.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findByIdUserTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user1));

        UserDto found = userService.findById(user1.getId());

        assertNotNull(found);
        assertEquals(userMapper.toDto(user1), found);
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteUserTest() {
        when(userRepository.getReferenceById(anyLong())).thenReturn((user1));

        UserDto delete = userService.delete(user1.getId());

        assertEquals(userMapper.toDto(user1), delete);
        verify(userRepository, times(1)).delete(user1);
    }


}