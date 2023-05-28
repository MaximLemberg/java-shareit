package ru.practicum.shareit.user.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    public static final String URL_TEMPLATE = "/users";
    public static final String APPLICATION_JSON = "application/json";
    public static final String X_SHARER_USER_ID = "X-Sharer-User-Id";

    public static final String NAME_USER_DTO = "testUserDtoName";

    public static final String EMAIL_USER_DTO = "testUserDtoEMail@yandex.ru";
    public static final long ID_1 = 1L;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserDto userDto1;

    @BeforeEach
    void setUp() {
        userDto1 = new UserDto(ID_1, NAME_USER_DTO, EMAIL_USER_DTO);
    }

    @SneakyThrows
    @Test
    void addUserCorrect() {
        when(userService.add(userDto1)).thenReturn(userDto1);

        mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto1))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userDto1)))
                .andReturn().getResponse().getContentAsString();

        verify(userService, times(1)).add(userDto1);
    }

    @SneakyThrows
    @Test
    void addUserNameNotValidNull() {
        UserDto userDto = new UserDto(ID_1, null, EMAIL_USER_DTO);


        mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto))
                        .header(X_SHARER_USER_ID, ID_1)
                )
                .andExpect(status().isBadRequest());

        verifyNoInteractions(userService);
    }


    @SneakyThrows
    @Test
    void addUserNameNotValidBig64() {
        UserDto userDto = new UserDto(ID_1, String.valueOf(new char[100]), EMAIL_USER_DTO);

        mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto))
                        .header(X_SHARER_USER_ID, ID_1)
                )
                .andExpect(status().isBadRequest());

        verifyNoInteractions(userService);
    }

    @SneakyThrows
    @Test
    void addUserNotValidEmailNull() {
        UserDto userDto = new UserDto(ID_1, NAME_USER_DTO, null);

        mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto))
                        .header(X_SHARER_USER_ID, ID_1)
                )
                .andExpect(status().isBadRequest());

        verifyNoInteractions(userService);
    }

    @SneakyThrows
    @Test
    void addUserNotValidEmailBlank() {
        UserDto userDto = new UserDto(ID_1, NAME_USER_DTO, " ");

        mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto))
                        .header(X_SHARER_USER_ID, ID_1)
                )
                .andExpect(status().isBadRequest());

        verifyNoInteractions(userService);
    }

    @SneakyThrows
    @Test
    void addUserNotValidEmail() {
        UserDto userDto = new UserDto(ID_1, NAME_USER_DTO, "dfdfdcxvvcxvcxvcxvcxvcxv");

        mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto))
                        .header(X_SHARER_USER_ID, ID_1)
                )
                .andExpect(status().isBadRequest());

        verifyNoInteractions(userService);
    }

    @SneakyThrows
    @Test
    void updateUserCorrect() {
        long userId = ID_1;
        when(userService.update(userDto1, userId)).thenReturn(userDto1);

        mockMvc.perform(patch(URL_TEMPLATE + "/{userId}", userId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto1))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userDto1)));

        verify(userService, times(1)).update(userDto1, userId);
    }


    @SneakyThrows
    @Test
    void removeUserCorrect() {
        long userId = ID_1;
        when(userService.delete(userId)).thenReturn(userDto1);

        mockMvc.perform(delete(URL_TEMPLATE + "/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userDto1)));

        verify(userService, times(1)).delete(userId);
    }

    @SneakyThrows
    @Test
    void findAllUserCorrect() {
        when(userService.findAll()).thenReturn(List.of(userDto1));

        mockMvc.perform(get(URL_TEMPLATE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));

        verify(userService, times(1)).findAll();
    }

    @SneakyThrows
    @Test
    void findByIdUserCorrect() {
        long userId = ID_1;
        when(userService.findById(userId)).thenReturn(userDto1);

        mockMvc.perform(get(URL_TEMPLATE + "/{userId}", userId)
                        .header(X_SHARER_USER_ID, userId)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userDto1)));

        verify(userService, times(1)).findById(userId);
    }


}