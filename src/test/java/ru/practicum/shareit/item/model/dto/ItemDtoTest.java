package ru.practicum.shareit.item.model.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ItemDtoTest {

    public static final long ID_1 = 1L;

    public static final String ITEM_NAME = "testItemName";

    public static final String ITEM_DESCRIPTION = "testItemDescription";


    public final ItemDto itemDto = new ItemDto(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true, 1L);

    @Test
    void testGetter() {
        ItemDto newItemDto = new ItemDto(itemDto.getId(), itemDto.getName(), itemDto.getDescription(), itemDto.getAvailable(),
                1L);
        assertSame(itemDto.getId(), newItemDto.getId());
        assertEquals(itemDto.getName(), newItemDto.getName());
        assertEquals(itemDto.getDescription(), newItemDto.getDescription());
        assertEquals(itemDto.getAvailable(), newItemDto.getAvailable());
        assertSame(itemDto.getRequestId(), newItemDto.getRequestId());
    }
}
