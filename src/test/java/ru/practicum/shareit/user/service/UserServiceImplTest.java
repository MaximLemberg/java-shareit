package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.common.exception.EntityNotFoundException;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;
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
class UserServiceImplTest {

    public static final String NAME_USER_DTO = "testUserDtoName";

    public static final String EMAIL_USER_DTO = "testUserDtoEMail@yandex.ru";

    public static final long ID_1 = 1L;

    @Mock
    private UserRepository userRepository;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    private UserServiceImpl userService;
    private User user1;

    private User user2;
    private UserDto userDto1;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(
                userRepository,
                userMapper
        );

        user1 = new User(ID_1, NAME_USER_DTO, EMAIL_USER_DTO);
        user2 = new User(2L, NAME_USER_DTO, EMAIL_USER_DTO);
        userDto1 = new UserDto(ID_1, NAME_USER_DTO, EMAIL_USER_DTO);
    }


    @Test
    void addUserTest() {
        when(userRepository.save(any(User.class))).thenAnswer(returnsFirstArg());

        UserDto added = userService.add(userDto1);

        assertEquals(userMapper.toDto(user1), added);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUserTest() {
        when(userRepository.getReferenceById(anyLong())).thenReturn(user1);
        when(userRepository.save(any(User.class))).thenAnswer(returnsFirstArg());

        UserDto userDto2 = new UserDto(user1.getId(), "new" + NAME_USER_DTO, "new" + EMAIL_USER_DTO);
        UserDto updated = userService.update(userDto2, user1.getId());

        assertEquals(userDto2, updated);
        verify(userRepository, times(1)).getReferenceById(anyLong());
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
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

    @Test
    void findAllUserTest() {
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserDto> found = userService.findAll();

        assertNotNull(found);
        assertEquals(2, found.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void checkMethodUserTest() {
        final EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.check(userRepository, 100L));

        Assertions.assertEquals("Object not Found", exception.getMessage());
    }






}