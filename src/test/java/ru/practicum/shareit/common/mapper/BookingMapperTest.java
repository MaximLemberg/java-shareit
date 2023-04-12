package ru.practicum.shareit.common.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingDtoAdd;
import ru.practicum.shareit.booking.model.dto.BookingDtoByItem;
import ru.practicum.shareit.booking.model.enums.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingMapperTest {

    private final BookingMapper bookingMapper = Mappers.getMapper(BookingMapper.class);

    public static final long ID_1 = 1L;

    public static final String ITEM_NAME = "testItemName";

    public static final String ITEM_DESCRIPTION = "testItemDescription";

    public static final String DESCRIPTION_ITEM_REQUEST = "testItemRequestDescription";

    public static final String EMAIL_USER = "testUserEMail@yandex.ru";

    public static final String NAME_USER = "testUserName";

    public final User user = new User(ID_1, NAME_USER, EMAIL_USER);

    public final ItemRequest itemRequest = new ItemRequest(ID_1,
            DESCRIPTION_ITEM_REQUEST,
            user,
            LocalDateTime.now(),
            null);

    public final UserDto userDto = new UserDto(ID_1, NAME_USER, EMAIL_USER);


    public final ItemDto itemDto = new ItemDto(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true, 1L);


    public final BookingDto bookingDto = new BookingDto(ID_1, userDto, itemDto, null, null, Status.APPROVED);

    public final Item item = new Item(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true, itemRequest, user);

    public final Booking booking1 = new Booking(ID_1, user, item, null, null, Status.APPROVED);

    public final BookingDtoAdd bookingDtoAdd = new BookingDtoAdd(ID_1, item.getId(), null, null, Status.APPROVED);


    @Test
    void toDtoTest() {
        BookingDto bookingDto = bookingMapper.toDto(booking1);
        assertTrue(booking1.getId() == bookingDto.getId());
        assertTrue(booking1.getBooker().getName().equals(bookingDto.getBooker().getName()));
        assertTrue(booking1.getItem().getName().equals(bookingDto.getItem().getName()));
        assertNull(bookingMapper.toDto(null));
    }

    @Test
    void toDtoEntity() {
        Booking booking = bookingMapper.toEntity(bookingDtoAdd, itemDto, userDto);
        assertTrue(booking.getId() == bookingDto.getId());
        assertTrue(booking.getBooker().getName().equals(bookingDto.getBooker().getName()));
        assertTrue(booking.getItem().getName().equals(bookingDto.getItem().getName()));
        assertNull(bookingMapper.toEntity(null, null, null));
    }

    @Test
    void toDtoEntityDefault() {
        Booking booking = bookingMapper.toEntity(bookingDto);
        assertTrue(booking.getId() == bookingDto.getId());
        assertTrue(booking.getBooker().getName().equals(bookingDto.getBooker().getName()));
        assertTrue(booking.getItem().getName().equals(bookingDto.getItem().getName()));
        assertNull(bookingMapper.toEntity(null));
    }

    @Test
    void toUpdate() {
        Booking booking = bookingMapper.update(bookingDto, booking1);
        assertTrue(booking.getId() == bookingDto.getId());
        assertTrue(booking.getBooker().getName().equals(bookingDto.getBooker().getName()));
        assertTrue(booking.getItem().getName().equals(bookingDto.getItem().getName()));
        assertNull(bookingMapper.update(null, null));

    }

    @Test
    void toBookingDtoByItem() {
        BookingDtoByItem bookingDtoByItem = bookingMapper.toBookingDtoByItem(booking1, user.getId());
        assertTrue(bookingDtoByItem.getId() == booking1.getId());
        assertNull(bookingMapper.toBookingDtoByItem(null, null));
    }

}
