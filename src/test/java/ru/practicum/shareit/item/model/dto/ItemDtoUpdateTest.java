package ru.practicum.shareit.item.model.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ItemDtoUpdateTest {
    public static final long ID_1 = 1L;

    public static final String ITEM_NAME = "testItemName";

    public static final String ITEM_DESCRIPTION = "testItemDescription";


    public final ItemDtoUpdate itemDtoUpdate = new ItemDtoUpdate(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true);

    @Test
    void testGetter() {
        ItemDtoUpdate newItemDtoUpdate = new ItemDtoUpdate(itemDtoUpdate.getId(),
                itemDtoUpdate.getName(),
                itemDtoUpdate.getDescription(),
                itemDtoUpdate.getAvailable());
        assertSame(itemDtoUpdate.getId(), newItemDtoUpdate.getId());
        assertEquals(itemDtoUpdate.getName(), newItemDtoUpdate.getName());
        assertEquals(itemDtoUpdate.getDescription(), newItemDtoUpdate.getDescription());
        assertEquals(itemDtoUpdate.getAvailable(), newItemDtoUpdate.getAvailable());
    }
}
