package ru.practicum.shareit.item.model;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;



class ItemTest {

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

    @Test
    void testToString() {
        System.out.println(item);
    }

    @Test
    void testGetterAndSetter() {
        Item newItem = new Item(item.getId(), item.getName(), item.getDescription(), item.getAvailable(),
                item.getRequest(), item.getOwner());
        newItem.setId(item.getId());
        newItem.setName(item.getName());
        newItem.setDescription(item.getDescription());
        newItem.setAvailable(item.getAvailable());
        newItem.setRequest(item.getRequest());
        newItem.setOwner(item.getOwner());
        assertSame(item.getId(), newItem.getId());
        assertEquals(item.getName(), newItem.getName());
        assertEquals(item.getDescription(), newItem.getDescription());
        assertEquals(item.getAvailable(), newItem.getAvailable());
        assertEquals(item.getRequest(), newItem.getRequest());
        assertEquals(item.getOwner(), newItem.getOwner());
    }

    @Test
    void testEquals() {
        Item item = new Item();
        Item item2 = new Item();
        assertFalse(item.equals(null));
        assertTrue(item.equals(item));
        assertFalse(item.equals(item2));
    }

    @Test
    void testHashCode() {
        Item item = new Item();
        int code = item.hashCode();
        assertEquals(item.getClass().hashCode(), code);
    }
}