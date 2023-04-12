package ru.practicum.shareit.booking.model.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.model.enums.Status;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.user.model.dto.UserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class BookingDtoTest {
    public static final long ID_1 = 1L;

    public static final String ITEM_NAME = "testItemName";

    public static final String ITEM_DESCRIPTION = "testItemDescription";


    public static final String EMAIL_USER = "testUserEMail@yandex.ru";

    public static final String NAME_USER = "testUserName";


    public final UserDto userDto = new UserDto(ID_1, NAME_USER, EMAIL_USER);


    public final ItemDto itemDto = new ItemDto(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true, 1L);

    public final BookingDto bookingDto = new BookingDto(ID_1, userDto, itemDto, null, null, Status.APPROVED);

    @Test
    void testGetter() {
        BookingDto newBookingDto = new BookingDto(bookingDto.getId(), bookingDto.getBooker(), bookingDto.getItem(), bookingDto.getStart(),
                bookingDto.getEnd(), bookingDto.getStatus());
        assertSame(bookingDto.getId(), newBookingDto.getId());
        assertEquals(bookingDto.getBooker(), newBookingDto.getBooker());
        assertEquals(bookingDto.getItem(), newBookingDto.getItem());
        assertEquals(bookingDto.getStart(), newBookingDto.getStart());
        assertEquals(bookingDto.getEnd(), newBookingDto.getEnd());
        assertEquals(bookingDto.getStatus(), newBookingDto.getStatus());
    }
}