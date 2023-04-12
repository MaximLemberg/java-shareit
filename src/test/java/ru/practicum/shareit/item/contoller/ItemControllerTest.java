package ru.practicum.shareit.item.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.comment.model.dto.CommentDto;
import ru.practicum.shareit.item.comment.model.dto.CommentDtoResponse;
import ru.practicum.shareit.item.comment.service.CommentService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemDtoFullResponse;
import ru.practicum.shareit.item.model.dto.ItemDtoUpdate;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {

    public static final String URL_TEMPLATE = "/items";
    public static final String APPLICATION_JSON = "application/json";
    public static final String X_SHARER_USER_ID = "X-Sharer-User-Id";
    public static final long ID_1 = 1L;

    public static final long ID_2 = 2L;

    public static final String NAME_USER = "testUserName";

    public static final String EMAIL_USER = "testUserEMail@yandex.ru";

    public static final String ITEM_NAME = "testItemName";

    public static final String ITEM_DESCRIPTION = "testItemDescription";

    public static final String DESCRIPTION_ITEM_REQUEST = "testItemRequestDescription";

    public static final String COMMENT_TEXT = "COMMENT_TEXT";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;
    @MockBean
    private CommentService commentService;

    private User user1;

    private UserDto userDto1;

    private Item item1;
    private ItemDto itemDto1;

    private ItemDtoUpdate itemDtoUpdate1;

    private ItemRequest itemRequest1;
    private ItemDtoFullResponse itemDtoFullResponse1;
    private CommentDto commentDto1;
    private CommentDtoResponse commentDtoResponse1;

    @BeforeEach
    void setUp() {
        commentDto1 = new CommentDto(ID_1, COMMENT_TEXT, itemDto1, userDto1, null);
        user1 = new User(ID_1, NAME_USER, EMAIL_USER);
        userDto1 = new UserDto(ID_1, NAME_USER, EMAIL_USER);
        item1 = new Item(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true, itemRequest1, user1);
        itemDto1 = new ItemDto(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true, null);
        itemDtoUpdate1 = new ItemDtoUpdate(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true);
        itemRequest1 = new ItemRequest(ID_1,
                DESCRIPTION_ITEM_REQUEST,
                user1,
                LocalDateTime.now(),
                null);

        itemDtoFullResponse1 = new ItemDtoFullResponse(ID_1,
                ITEM_NAME,
                ITEM_DESCRIPTION,
                true,
                null,
                null,
                null);

        commentDtoResponse1 = new CommentDtoResponse(
                ID_1,
                COMMENT_TEXT,
                userDto1.getName(),
                null);
    }

    @SneakyThrows
    @Test
    void addItemTest() {
        long userId = ID_1;
        when(itemService.add(itemDto1, userId)).thenReturn(itemDto1);

        mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDto1))
                        .header(X_SHARER_USER_ID, userId)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(itemDto1)))
                .andReturn().getResponse().getContentAsString();

        verify(itemService, times(1)).add(itemDto1, userId);
    }

    @Test
    void updateItemTest() throws Exception {
        when(itemService.update(itemDtoUpdate1, item1.getId(), user1.getId())).thenReturn(itemDto1);

        mockMvc.perform(patch(URL_TEMPLATE + "/{itemId}", item1.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDtoUpdate1))
                        .header(X_SHARER_USER_ID, user1.getId())
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(itemDtoUpdate1)))
                .andReturn().getResponse().getContentAsString();

        verify(itemService, times(1)).update(itemDtoUpdate1, item1.getId(), user1.getId());
    }

    @SneakyThrows
    @Test
    void findByIdTest() {
        when(itemService.findById(item1.getId(), user1.getId())).thenReturn(itemDtoFullResponse1);

        mockMvc.perform(get(URL_TEMPLATE + "/{itemId}", item1.getId())
                        .header(X_SHARER_USER_ID, user1.getId())
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(itemDtoFullResponse1)));

        verify(itemService, times(1)).findById(item1.getId(), user1.getId());
    }

    @SneakyThrows
    @Test
    void findAllByUserIdTest() {
        when(itemService.findAllByUserId(user1.getId())).thenReturn(List.of(itemDtoFullResponse1));

        mockMvc.perform(get(URL_TEMPLATE)
                        .header(X_SHARER_USER_ID, user1.getId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));

        verify(itemService, times(1)).findAllByUserId(user1.getId());
    }

    @SneakyThrows
    @Test
    void searchByNameOrDescriptionTest() {
        String text = "text";
        when(itemService.searchByNameOrDescription(text)).thenReturn(List.of(itemDto1, itemDto1));

        mockMvc.perform(get(URL_TEMPLATE + "/search")
                        .param("text", text)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));

        verify(itemService, times(1)).searchByNameOrDescription(text);
    }

    @SneakyThrows
    @Test
    void addCommentTest() {
        when(commentService.add(commentDto1, item1.getId(), user1.getId())).thenReturn(commentDtoResponse1);

        mockMvc.perform(post(URL_TEMPLATE + "/{itemId}/comment", item1.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto1))
                        .header(X_SHARER_USER_ID, user1.getId())
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(commentDtoResponse1)))
                .andReturn().getResponse().getContentAsString();

        verify(commentService, times(1)).add(commentDto1, item1.getId(), user1.getId());
    }



































    private ItemDto makeItemDto(long id) {
        return new ItemDto(
                id,
                "name_" + id,
                "description_" + id,
                true,
                null
        );
    }



    private CommentDto makeCommentDto(long id) {
        return new CommentDto(
                id,
                "text",
                null,
                null,
                null
        );
    }


}