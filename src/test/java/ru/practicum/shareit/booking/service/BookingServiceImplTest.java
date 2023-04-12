package ru.practicum.shareit.booking.service;

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
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingDtoAdd;
import ru.practicum.shareit.booking.model.enums.Status;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.common.exception.EntityNotFoundException;
import ru.practicum.shareit.common.exception.NotAvailableException;
import ru.practicum.shareit.common.exception.UnsupportedStateException;
import ru.practicum.shareit.common.exception.ValidationException;
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
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {
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

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);
    private final BookingMapper bookingMapper = Mappers.getMapper(BookingMapper.class);
    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    private BookingServiceImpl bookingService;

    @Captor
    private ArgumentCaptor<Item> itemArgumentCaptor;

    private User user1;
    private User user2;
    private UserDto userDto1;

    private Item item1;
    private Item item2;
    private ItemDto itemDto1;
    private ItemRequest itemRequest1;


    private Comment comment1;
    private CommentDto commentDto1;

    private Booking booking1;
    private BookingDto bookingDto1;
    private BookingDtoAdd bookingAdd1;



    private ItemDtoUpdate itemDtoUpdate1;

    @BeforeEach
    void setUp() {
        bookingService = new BookingServiceImpl(
                bookingRepository,
                bookingMapper,
                userMapper,
                itemRepository,
                itemMapper,
                userRepository
        );

        user1 = new User(ID_1, NAME_USER, EMAIL_USER);
        user2 = new User(ID_2, NAME_USER, EMAIL_USER);
        userDto1 = new UserDto(ID_1, NAME_USER, EMAIL_USER);
        item1 = new Item(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true, itemRequest1, user1);

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


        itemDto1 = new ItemDto(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true, null);

        item2 = new Item(ID_2, ITEM_NAME, ITEM_DESCRIPTION, true, itemRequest1, user2);
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
    void addBookingTest() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(item2));
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user1));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(returnsFirstArg());

        BookingDto added = bookingService.add(bookingAdd1, user1.getId());

        assertEquals(bookingAdd1.getStart(), added.getStart());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void addBookingExceptionTest() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(item1));
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user1));

        final EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> bookingService.add(bookingAdd1, user1.getId()));

        Assertions.assertEquals("Item Unavailable", exception.getMessage());
    }

    @Test
    void addBookingExceptionAvailableTest() {
        item2.setAvailable(false);
        when(itemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(item2));
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user1));

        final NotAvailableException exception = Assertions.assertThrows(
                NotAvailableException.class,
                () -> bookingService.add(bookingAdd1, user1.getId()));

        Assertions.assertEquals("Item Unavailable", exception.getMessage());
    }

    @Test
    void upgradeBookingTest() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.ofNullable(booking1));
        when(bookingRepository.save(any())).thenAnswer(returnsFirstArg());

        BookingDto updated = bookingService.upgrade(booking1.getId(), user1.getId(), true);


        assertEquals(Status.APPROVED, updated.getStatus());
        verify(bookingRepository, times(1)).save(booking1);
    }

    @Test
    void upgradeBookingNotOwnerTest() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.ofNullable(booking1));

        final EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> bookingService.upgrade(booking1.getId(), 100L, true));

        Assertions.assertEquals("Only owner can change status", exception.getMessage());

    }

    @Test
    void upgradeBookingAlreadyApprovedTest() {
        booking1 = new Booking(ID_1,
                user1,
                item1,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(20),
                Status.APPROVED);
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.ofNullable(booking1));

        final ValidationException exception = Assertions.assertThrows(
                ValidationException.class,
                () -> bookingService.upgrade(booking1.getId(), user1.getId(), true));

        Assertions.assertEquals("Status already set", exception.getMessage());

    }

    @Test
    void findByIdBookingTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking1));

        BookingDto found = bookingService.findById(booking1.getId(),"ALL", user1.getId());

        assertEquals(bookingMapper.toDto(booking1), found);
        verify(bookingRepository, times(1)).findById(booking1.getId());
    }


    @Test
    void findAllByIdBookingTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(bookingRepository.findAll()).thenReturn(List.of(booking1));

        List<BookingDto> found = bookingService.findAllById(
                user1.getId(),
                "ALL",
                9999,
                9999);

        assertEquals(1, found.size());
    }

    @Test
    void findAllByIdBookingWaitingTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(bookingRepository.findAll()).thenReturn(List.of(booking1));

        List<BookingDto> found = bookingService.findAllById(
                user1.getId(),
                "WAITING",
                9999,
                9999);

        assertEquals(1, found.size());
    }

    @Test
    void findAllByIdBookingRejectedTest() {
        booking1.setStatus(Status.REJECTED);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(bookingRepository.findAll()).thenReturn(List.of(booking1));

        List<BookingDto> found = bookingService.findAllById(
                user1.getId(),
                "REJECTED",
                9999,
                9999);

        assertEquals(1, found.size());
    }

    @Test
    void findAllByIdBookingCurrentTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(bookingRepository.findAll()).thenReturn(List.of(booking1));

        List<BookingDto> found = bookingService.findAllById(
                user1.getId(),
                "CURRENT",
                9999,
                9999);

        assertEquals(0, found.size());
    }

    @Test
    void findAllByIdBookingPastTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(bookingRepository.findAll()).thenReturn(List.of(booking1));

        List<BookingDto> found = bookingService.findAllById(
                user1.getId(),
                "PAST",
                9999,
                9999);

        assertEquals(0, found.size());
    }

    @Test
    void findAllByIdBookingPage() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(bookingRepository.findAllByBookerId(anyLong(), any())).thenReturn(List.of(booking1));

        List<BookingDto> found = bookingService.findAllById(
                user1.getId(),
                "ALL",
                0,
                100);

        assertEquals(1, found.size());
    }

    @Test
    void findAllByIdBookingPageUnsupportedState() {

        final UnsupportedStateException exception = Assertions.assertThrows(
                UnsupportedStateException.class,
                () -> bookingService.findAllById(
                        user1.getId(),
                        "UNSUPPORTED_STATUS",
                        9999,
                        9999));

        Assertions.assertEquals("Unknown state: UNSUPPORTED_STATUS", exception.getMessage());
    }

    @Test
    void findAllAllByOwnerIdBookingTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(bookingRepository.findAll()).thenReturn(List.of(booking1));

        List<BookingDto> found = bookingService.findAllByOwnerId(
                user1.getId(),
                "ALL",
                9999,
                9999);

        assertEquals(1, found.size());
    }

    @Test
    void findAllAllByOwnerIdBookingWaitingTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(bookingRepository.findAll()).thenReturn(List.of(booking1));

        List<BookingDto> found = bookingService.findAllByOwnerId(
                user1.getId(),
                "WAITING",
                9999,
                9999);

        assertEquals(1, found.size());
    }

    @Test
    void findAllAllByOwnerIdBookingRejectedTest() {
        booking1.setStatus(Status.REJECTED);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(bookingRepository.findAll()).thenReturn(List.of(booking1));

        List<BookingDto> found = bookingService.findAllByOwnerId(
                user1.getId(),
                "REJECTED",
                9999,
                9999);

        assertEquals(1, found.size());
    }

    @Test
    void findAllAllByOwnerIdBookingCurrentTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(bookingRepository.findAll()).thenReturn(List.of(booking1));

        List<BookingDto> found = bookingService.findAllByOwnerId(
                user1.getId(),
                "CURRENT",
                9999,
                9999);

        assertEquals(0, found.size());
    }

    @Test
    void findAllAllByOwnerIdBookingPastTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(bookingRepository.findAll()).thenReturn(List.of(booking1));

        List<BookingDto> found = bookingService.findAllByOwnerId(
                user1.getId(),
                "PAST",
                9999,
                9999);

        assertEquals(0, found.size());
    }

    @Test
    void findAllByOwnerIdBookingPage() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(bookingRepository.findAllByItemOwnerId(anyLong(), any())).thenReturn(List.of(booking1));

        List<BookingDto> found = bookingService.findAllByOwnerId(
                user1.getId(),
                "ALL",
                0,
                100);

        assertEquals(1, found.size());
    }

    @Test
    void findAllByOwnerIdBookingPageUnsupportedState() {

        final UnsupportedStateException exception = Assertions.assertThrows(
                UnsupportedStateException.class,
                () -> bookingService.findAllByOwnerId(
                        user1.getId(),
                        "UNSUPPORTED_STATUS",
                        9999,
                        9999));

        Assertions.assertEquals("Unknown state: UNSUPPORTED_STATUS", exception.getMessage());
    }

    @Test
    void checkTimeTestStartNull() {
        bookingAdd1 = new BookingDtoAdd(ID_1, 1L,
                null,
                LocalDateTime.now().plusDays(20),
                Status.WAITING);
        final ValidationException exception = Assertions.assertThrows(
                ValidationException.class,
                () -> bookingService.checkTime(bookingAdd1));

        Assertions.assertEquals("Incorrect time", exception.getMessage());
    }

    @Test
    void checkTimeTestEndNull() {
        bookingAdd1 = new BookingDtoAdd(ID_1, 1L,
                LocalDateTime.now(),
                null,
                Status.WAITING);
        final ValidationException exception = Assertions.assertThrows(
                ValidationException.class,
                () -> bookingService.checkTime(bookingAdd1));

        Assertions.assertEquals("Incorrect time", exception.getMessage());
    }

    @Test
    void checkTimeTestNotValid() {
        bookingAdd1 = new BookingDtoAdd(ID_1, 1L,
                LocalDateTime.now(),
                LocalDateTime.now().minusDays(1),
                Status.WAITING);
        final ValidationException exception = Assertions.assertThrows(
                ValidationException.class,
                () -> bookingService.checkTime(bookingAdd1));

        Assertions.assertEquals("Incorrect time", exception.getMessage());
    }


}
