package ru.practicum.shareit.booking.model.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class BookingDtoByItemTest {

    public static final long ID_1 = 1L;

    public static final String EMAIL_USER = "testUserEMail@yandex.ru";

    public static final String NAME_USER = "testUserName";

    public final User user = new User(ID_1, NAME_USER, EMAIL_USER);

    public final BookingDtoByItem bookingDtoByItem = new BookingDtoByItem(ID_1, user.getId());

    @Test
    void testGetter() {
        BookingDtoByItem newBookingDtoByItem = new BookingDtoByItem(bookingDtoByItem.getId(),
                bookingDtoByItem.getBookerId());
        assertSame(bookingDtoByItem.getId(), newBookingDtoByItem.getId());
        assertEquals(bookingDtoByItem.getBookerId(), newBookingDtoByItem.getBookerId());

    }
}
