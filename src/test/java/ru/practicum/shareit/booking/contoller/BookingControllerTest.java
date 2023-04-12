package ru.practicum.shareit.booking.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingDtoAdd;
import ru.practicum.shareit.booking.model.enums.Status;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.comment.model.dto.CommentDto;
import ru.practicum.shareit.item.comment.model.dto.CommentDtoResponse;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemDtoFullResponse;
import ru.practicum.shareit.item.model.dto.ItemDtoUpdate;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookingController.class)
class BookingControllerTest {

    public static final String URL_TEMPLATE = "/bookings";
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
    private BookingService bookingService;

    @MockBean
    private  ItemRepository itemRepository;

    @MockBean
    private  UserRepository userRepository;

    private User user1;

    private UserDto userDto1;

    private Item item1;
    private ItemDto itemDto1;

    private ItemDtoUpdate itemDtoUpdate1;

    private ItemRequest itemRequest1;
    private ItemDtoFullResponse itemDtoFullResponse1;
    private CommentDto commentDto1;
    private CommentDtoResponse commentDtoResponse1;

    private Booking booking1;
    private BookingDto bookingDto1;
    private BookingDtoAdd bookingAdd1;

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

        booking1 = new Booking(ID_1,
                user1,
                item1,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(20),
                Status.WAITING);

        bookingDto1 = new BookingDto(ID_1, userDto1, itemDto1,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(20),
                Status.WAITING);

        bookingAdd1 = new BookingDtoAdd(ID_1, 1L,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(20),
                Status.WAITING);
    }

    @SneakyThrows
    @Test
    void addValidTest() {
        when(bookingService.add(bookingAdd1, user1.getId())).thenReturn(bookingDto1);

        mockMvc.perform(post(URL_TEMPLATE)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingAdd1))
                        .header(X_SHARER_USER_ID, user1.getId())
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bookingDto1)))
                .andReturn().getResponse().getContentAsString();

    }

    @SneakyThrows
    @Test
    void approveUpgradeValidTest() {
        when(bookingService.upgrade(booking1.getId(), user1.getId(), true)).thenReturn(bookingDto1);

        mockMvc.perform(patch(URL_TEMPLATE + "/{bookingId}", booking1.getId())
                        .header(X_SHARER_USER_ID, user1.getId())
                        .param("approved", String.valueOf(true))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bookingDto1)));

        verify(bookingService, times(1)).upgrade(booking1.getId(), user1.getId(), true);
    }

    @SneakyThrows
    @Test
    void findByIdValidTest() {
        when(bookingService.findById(booking1.getId(), "ALL", user1.getId())).thenReturn(bookingDto1);

        mockMvc.perform(get(URL_TEMPLATE + "/{bookingId}", booking1.getId())
                        .header(X_SHARER_USER_ID, user1.getId())
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bookingDto1)));

        verify(bookingService, times(1)).findById(booking1.getId(), "ALL", user1.getId());
    }

    @SneakyThrows
    @Test
    void findByIdBookerValidTest() {
        when(bookingService.findAllById(user1.getId(), "ALL", 9999, 9999))
                .thenReturn(List.of(bookingDto1));

        mockMvc.perform(get(URL_TEMPLATE)
                        .header(X_SHARER_USER_ID, user1.getId())
                )
                .andExpect(jsonPath("$.length()", is(1)));

        verify(bookingService, times(1)).findAllById(user1.getId(), "ALL", 9999, 9999);
    }

    @SneakyThrows
    @Test
    void findByIdOwnerValidTest() {
        when(bookingService.findAllByOwnerId(user1.getId(), "ALL", 9999, 9999))
                .thenReturn(List.of(bookingDto1));

        mockMvc.perform(get(URL_TEMPLATE + "/owner")
                        .header(X_SHARER_USER_ID, user1.getId())
                )
                .andExpect(jsonPath("$.length()", is(1)));

        verify(bookingService, times(1)).findAllByOwnerId(user1.getId(), "ALL", 9999, 9999);
    }

}
