package ru.practicum.shareit.item.comment.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.enums.Status;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.common.exception.NotAvailableException;
import ru.practicum.shareit.item.comment.mapper.CommentMapper;
import ru.practicum.shareit.item.comment.model.Comment;
import ru.practicum.shareit.item.comment.model.dto.CommentDto;
import ru.practicum.shareit.item.comment.storage.CommentRepository;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemDtoUpdate;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private CommentRepository commentRepository;

    public static final long ID_1 = 1L;

    public static final long ID_2 = 2L;

    public static final String COMMENT_TEXT = "COMMENT_TEXT";

    public static final String NAME_USER = "testUserName";

    public static final String EMAIL_USER = "testUserEMail@yandex.ru";

    public static final String ITEM_NAME = "testItemName";

    public static final String ITEM_DESCRIPTION = "testItemDescription";

    public static final String DESCRIPTION_ITEM_REQUEST = "testItemRequestDescription";

    private final ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);
    private final BookingMapper bookingMapper = Mappers.getMapper(BookingMapper.class);
    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    private CommentServiceImpl commentService;

    @Captor
    private ArgumentCaptor<Item> itemArgumentCaptor;

    private User user1;
    private UserDto userDto1;
    private Item item1;
    private Comment comment1;
    private Item item2;
    private Booking booking1;
    private CommentDto commentDto1;
    private ItemRequest itemRequest1;
    private ItemDto itemDto1;

    private ItemDtoUpdate itemDtoUpdate1;

    @BeforeEach
    void setUp() {
        commentService = new CommentServiceImpl(
                commentRepository,
                itemRepository,
                bookingRepository,
                commentMapper,
                userRepository
        );

        user1 = new User(ID_1, NAME_USER, EMAIL_USER);
        booking1 = new Booking(ID_1, user1, item1, null, null, Status.APPROVED);
        userDto1 = new UserDto(ID_1, NAME_USER, EMAIL_USER);
        itemDto1 = new ItemDto(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true, null);
        item1 = new Item(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true, itemRequest1, user1);
        item2 = new Item(ID_2, ITEM_NAME, ITEM_DESCRIPTION, true, itemRequest1, user1);
        comment1 = new Comment(ID_1, COMMENT_TEXT, item1, user1, null);
        commentDto1 = new CommentDto(ID_1, COMMENT_TEXT, itemDto1, userDto1, null);
        itemDtoUpdate1 = new ItemDtoUpdate(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true);
        itemRequest1 = new ItemRequest(ID_1,
                DESCRIPTION_ITEM_REQUEST,
                user1,
                LocalDateTime.now(),
                null);

    }

    @Test
    void addCommentNotValidTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user1));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(item1));

        final NotAvailableException exception = Assertions.assertThrows(
                NotAvailableException.class,
                () -> commentService.add(commentDto1, item1.getId(), user1.getId()));

        Assertions.assertEquals("Only completed booking", exception.getMessage());

    }

}