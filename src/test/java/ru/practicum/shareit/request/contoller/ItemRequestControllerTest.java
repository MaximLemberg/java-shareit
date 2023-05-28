package ru.practicum.shareit.request.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemRequestController.class)
class ItemRequestControllerTest {

    public static final String URL_TEMPLATE = "/requests";
    public static final String APPLICATION_JSON = "application/json";
    public static final String X_SHARER_USER_ID = "X-Sharer-User-Id";
    public static final long ID_1 = 1L;

    public static final String NAME_USER_DTO = "testUserDtoName";

    public static final String EMAIL_USER_DTO = "testUserDtoEMail@yandex.ru";


    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemRequestService requestService;

    private ItemRequestDto itemRequestDto1;
    private ItemRequest itemRequest1;

    @BeforeEach
    void setUp() {
        itemRequestDto1 = new ItemRequestDto(ID_1,
                "DescriptionTest",
                new UserDto(ID_1, NAME_USER_DTO, EMAIL_USER_DTO),
                LocalDateTime.now(),
                null);

        itemRequest1 = new ItemRequest(ID_1,
                "DescriptionTest",
                new User(ID_1, NAME_USER_DTO, EMAIL_USER_DTO),
                LocalDateTime.now(),
                null);
    }

    @SneakyThrows
    @Test
    void addItemRequestValidTest() {
        long userId = ID_1;
        when(requestService.add(itemRequestDto1, userId)).thenReturn(itemRequestDto1);

        mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemRequestDto1))
                        .header(X_SHARER_USER_ID, userId)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(itemRequestDto1)))
                .andReturn().getResponse().getContentAsString();

        verify(requestService, times(1)).add(itemRequestDto1, userId);
    }

    @SneakyThrows
    @Test
    void addItemRequestDescriptionIsNullTest() {
        ItemRequestDto testItemRequestDto = new ItemRequestDto(ID_1,
                null,
                new UserDto(ID_1, NAME_USER_DTO, EMAIL_USER_DTO),
                LocalDateTime.now(),
                null);

        mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testItemRequestDto))
                        .header(X_SHARER_USER_ID, ID_1)
                )
                .andExpect(status().isBadRequest());

        verifyNoInteractions(requestService);
    }

    @SneakyThrows
    @Test
    void addItemRequestDescriptionIsBlankTest() {
        ItemRequestDto testItemRequestDto = new ItemRequestDto(ID_1,
                " ",
                new UserDto(ID_1, NAME_USER_DTO, EMAIL_USER_DTO),
                LocalDateTime.now(),
                null);

        mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testItemRequestDto))
                        .header(X_SHARER_USER_ID, ID_1)
                )
                .andExpect(status().isBadRequest());

        verifyNoInteractions(requestService);
    }


    @SneakyThrows
    @Test
    void addItemRequestItemIdIsBlankTest() {
        ItemRequestDto testItemRequestDto = new ItemRequestDto(ID_1,
                " ",
                new UserDto(ID_1, NAME_USER_DTO, EMAIL_USER_DTO),
                LocalDateTime.now(),
                null);

        mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testItemRequestDto))
                        .header(X_SHARER_USER_ID, ID_1)
                )
                .andExpect(status().isBadRequest());

        verifyNoInteractions(requestService);
    }

    @SneakyThrows
    @Test
    void findAllByUserIdValidTest() {
        long userId = ID_1;
        when(requestService.findAllByUserId(userId)).thenReturn(List.of(itemRequestDto1));

        mockMvc.perform(get(URL_TEMPLATE)
                        .header(X_SHARER_USER_ID, userId)
                )
                .andExpect(jsonPath("$.length()", is(1)));

        verify(requestService, times(1)).findAllByUserId(userId);
    }

    @SneakyThrows
    @Test
    void findByIdValidTest() {
        long userId = ID_1;
        long requestId = ID_1;
        when(requestService.findById(requestId, userId)).thenReturn(itemRequestDto1);

        mockMvc.perform(get(URL_TEMPLATE + "/{requestId}", requestId)
                        .header(X_SHARER_USER_ID, userId)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(itemRequestDto1)));

        verify(requestService, times(1)).findById(requestId, userId);
    }

    @SneakyThrows
    @Test
    void findAllByPageValidTest() {
        long userId = ID_1;
        int from = 1;
        int size = 1;
        when(requestService.findAllByIdWithPage(userId, from, size)).thenReturn(List.of(itemRequestDto1));

        mockMvc.perform(get(URL_TEMPLATE + "/all")
                        .header(X_SHARER_USER_ID, userId)
                        .param("from", String.valueOf(from))
                        .param("size", String.valueOf(size))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));

        verify(requestService, times(1)).findAllByIdWithPage(userId, from, size);
    }



}