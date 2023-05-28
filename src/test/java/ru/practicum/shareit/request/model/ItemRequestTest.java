package ru.practicum.shareit.request.model;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class ItemRequestTest {

    public static final long ID_1 = 1L;

    public static final String DESCRIPTION_ITEM_REQUEST = "testItemRequestDescription";

    public static final String EMAIL_USER = "testUserEMail@yandex.ru";

    public static final String NAME_USER = "testUserName";

    public final User user = new User(ID_1, NAME_USER, EMAIL_USER);

    public final ItemRequest itemRequest = new ItemRequest(ID_1,
            DESCRIPTION_ITEM_REQUEST,
            user,
            LocalDateTime.now(),
            null);

    @Test
    void testToString() {
        System.out.println(itemRequest);
    }


    @Test
    void testGetterAndSetter() {
        ItemRequest newItemRequest = new ItemRequest(itemRequest.getId(),
                itemRequest.getDescription(),
                itemRequest.getRequester(),
                LocalDateTime.now(),
                null);
        newItemRequest.setId(itemRequest.getId());
        newItemRequest.setDescription(itemRequest.getDescription());
        newItemRequest.setRequester(itemRequest.getRequester());
        newItemRequest.setCreated(itemRequest.getCreated());
        newItemRequest.setItems(itemRequest.getItems());
        assertTrue(itemRequest.getId() == newItemRequest.getId());
        assertTrue(itemRequest.getDescription().equals(newItemRequest.getDescription()));
        assertTrue(itemRequest.getRequester().equals(newItemRequest.getRequester()));
        assertTrue(itemRequest.getCreated().equals(newItemRequest.getCreated()));
        assertTrue(itemRequest.getItems() == null);

    }

    @Test
    void testEquals() {
        ItemRequest itemRequest = new ItemRequest();
        ItemRequest itemRequest2 = new ItemRequest();
        assertFalse(itemRequest.equals(null));
        assertTrue(itemRequest.equals(itemRequest));
        assertFalse(itemRequest.equals(itemRequest2));
    }

    @Test
    void testHashCode() {
        ItemRequest itemRequest = new ItemRequest();
        int code = itemRequest.hashCode();
        assertEquals(itemRequest.getClass().hashCode(), code);
    }
}