package ru.practicum.shareit.booking.model;


import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.model.enums.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class BookingTest {

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

    public final Item item = new Item(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true, itemRequest, user);

    public final Booking booking = new Booking(ID_1, user, item, null, null, Status.APPROVED);

    @Test
    void testToString() {
        System.out.println(booking);
    }

    @Test
    void testGetterAndSetter() {
        Booking newBooking = new Booking(booking.getId(), booking.getBooker(), booking.getItem(), booking.getStart(),
                booking.getEnd(), booking.getStatus());
        newBooking.setId(booking.getId());
        newBooking.setBooker(booking.getBooker());
        newBooking.setItem(booking.getItem());
        newBooking.setStart(booking.getStart());
        newBooking.setEnd(booking.getEnd());
        newBooking.setStatus(booking.getStatus());
        assertSame(booking.getId(), newBooking.getId());
        assertEquals(booking.getBooker(), newBooking.getBooker());
        assertEquals(booking.getItem(), newBooking.getItem());
        assertEquals(booking.getStart(), newBooking.getStart());
        assertEquals(booking.getEnd(), newBooking.getEnd());
        assertEquals(booking.getStatus(), newBooking.getStatus());
    }

    @Test
    void testEquals() {
        Booking booking = new Booking();
        Booking booking2 = new Booking();
        assertFalse(booking.equals(null));
        assertTrue(booking.equals(booking));
        assertFalse(booking.equals(booking2));
    }

    @Test
    void testHashCode() {
        Booking booking = new Booking();
        int code = booking.hashCode();
        assertEquals(booking.getClass().hashCode(), code);
    }
}