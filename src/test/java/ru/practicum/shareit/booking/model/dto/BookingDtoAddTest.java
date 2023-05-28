package ru.practicum.shareit.booking.model.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.model.enums.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class BookingDtoAddTest {
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

    public final BookingDtoAdd bookingDtoAdd = new BookingDtoAdd(ID_1, item.getId(),  null, null, Status.APPROVED);

    @Test
    void testGetter() {
        BookingDtoAdd newBookingDtoAdd = new BookingDtoAdd(bookingDtoAdd.getId(),
                bookingDtoAdd.getItemId(),
                bookingDtoAdd.getStart(),
                bookingDtoAdd.getEnd(),
                bookingDtoAdd.getStatus());
        assertSame(bookingDtoAdd.getId(), newBookingDtoAdd.getId());
        assertEquals(bookingDtoAdd.getItemId(), newBookingDtoAdd.getItemId());
        assertEquals(bookingDtoAdd.getStart(), newBookingDtoAdd.getStart());
        assertEquals(bookingDtoAdd.getEnd(), newBookingDtoAdd.getEnd());
        assertEquals(bookingDtoAdd.getStatus(), newBookingDtoAdd.getStatus());
    }
}
