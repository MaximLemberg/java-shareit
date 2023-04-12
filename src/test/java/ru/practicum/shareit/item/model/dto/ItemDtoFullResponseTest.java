package ru.practicum.shareit.item.model.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ItemDtoFullResponseTest {
    public static final long ID_1 = 1L;

    public static final String ITEM_NAME = "testItemName";

    public static final String ITEM_DESCRIPTION = "testItemDescription";


    public final ItemDtoFullResponse itemDtoFullResponse = new ItemDtoFullResponse(ID_1,
            ITEM_NAME,
            ITEM_DESCRIPTION,
            true,
            null,
            null,
            null);

    @Test
    void testGetter() {
        ItemDtoFullResponse itemDtoFullResponse1 = new ItemDtoFullResponse(
                itemDtoFullResponse.getId(),
                itemDtoFullResponse.getName(),
                itemDtoFullResponse.getDescription(),
                itemDtoFullResponse.getAvailable(),
                itemDtoFullResponse.getNextBooking(),
                itemDtoFullResponse.getLastBooking(),
                itemDtoFullResponse.getComments());
        assertSame(itemDtoFullResponse.getId(), itemDtoFullResponse1.getId());
        assertEquals(itemDtoFullResponse.getName(), itemDtoFullResponse1.getName());
        assertEquals(itemDtoFullResponse.getDescription(), itemDtoFullResponse1.getDescription());
        assertEquals(itemDtoFullResponse.getAvailable(), itemDtoFullResponse1.getAvailable());
        assertEquals(itemDtoFullResponse.getNextBooking(), itemDtoFullResponse1.getNextBooking());
        assertEquals(itemDtoFullResponse.getLastBooking(), itemDtoFullResponse1.getLastBooking());
        assertEquals(itemDtoFullResponse.getComments(), itemDtoFullResponse1.getComments());

    }
}
