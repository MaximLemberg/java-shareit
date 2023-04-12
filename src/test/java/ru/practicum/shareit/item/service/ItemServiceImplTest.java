package ru.practicum.shareit.item.service;

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
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.common.exception.EntityNotFoundException;
import ru.practicum.shareit.item.comment.mapper.CommentMapper;
import ru.practicum.shareit.item.comment.model.Comment;
import ru.practicum.shareit.item.comment.storage.CommentRepository;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemDtoFullResponse;
import ru.practicum.shareit.item.model.dto.ItemDtoUpdate;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

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

    public static final String NAME_USER = "testUserName";

    public static final String EMAIL_USER = "testUserEMail@yandex.ru";

    public static final String ITEM_NAME = "testItemName";

    public static final String ITEM_DESCRIPTION = "testItemDescription";

    public static final String DESCRIPTION_ITEM_REQUEST = "testItemRequestDescription";

    private final ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);
    private final BookingMapper bookingMapper = Mappers.getMapper(BookingMapper.class);
    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    private ItemServiceImpl itemService;

    @Captor
    private ArgumentCaptor<Item> itemArgumentCaptor;

    private User user1;
    private Item item1;

    private Item item2;
    private Booking booking1;
    private Comment comment1;
    private ItemRequest itemRequest1;
    private ItemDto itemDto1;

    private ItemDtoUpdate itemDtoUpdate1;

    @BeforeEach
    void setUp() {
        itemService = new ItemServiceImpl(
                itemRepository,
                itemMapper,
                bookingMapper,
                commentMapper,
                userRepository,
                bookingRepository,
                commentRepository
        );

        user1 = new User(ID_1, NAME_USER, EMAIL_USER);
        itemDto1 = new ItemDto(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true, null);
        item1 = new Item(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true, itemRequest1, user1);
        item2 = new Item(ID_2, ITEM_NAME, ITEM_DESCRIPTION, true, itemRequest1, user1);
        itemDtoUpdate1 = new ItemDtoUpdate(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true);
        itemRequest1 = new ItemRequest(ID_1,
                DESCRIPTION_ITEM_REQUEST,
                user1,
                LocalDateTime.now(),
                null);


    }

    @Test
    void addItemTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user1));
        when(itemRepository.save(any(Item.class))).thenAnswer(returnsFirstArg());

        ItemDto added = itemService.add(itemDto1, user1.getId());

        assertEquals(itemMapper.toDto(item1), added);
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void updateItemTest() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(item1));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(item2));
        when(itemRepository.save(any(Item.class))).thenAnswer(returnsFirstArg());

        ItemDto updated = itemService.update(itemDtoUpdate1, item1.getId(), user1.getId());

        final EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> itemService.update(itemDtoUpdate1, item2.getId(), 1000L));

        Assertions.assertEquals("Only owner can edit", exception.getMessage());

        assertEquals(itemDtoUpdate1.getDescription(), updated.getDescription());
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void findAllByUserIdTest() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(item1));
        when(itemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(item2));
        when(itemRepository.findAll()).thenReturn(List.of(item1, item2));

        List<ItemDtoFullResponse> found = itemService.findAllByUserId(user1.getId());

        assertNotNull(found);
        assertEquals(2, found.size());
        verify(itemRepository, times(2)).findById(anyLong());
    }

    @Test
    void searchByNameOrDescriptionTest() {
        when(itemRepository.findAll()).thenReturn(List.of(item1, item2));

        List<ItemDto> found = itemService.searchByNameOrDescription(item1.getDescription());

        assertNotNull(found);
        assertEquals(0, found.size());
    }






}