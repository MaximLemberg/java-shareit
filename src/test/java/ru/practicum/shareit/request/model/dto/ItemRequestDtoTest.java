package ru.practicum.shareit.request.model.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.model.dto.UserDto;

import static org.junit.jupiter.api.Assertions.*;

public class ItemRequestDtoTest {

    public static final long ID_1 = 1L;

    public static final String DESCRIPTION_ITEM_REQUEST_DTO = "testItemRequestDtoDescription";

    public static final String NAME_USER_DTO = "testUserDtoName";

    public static final String EMAIL_USER_DTO = "testUserDtoEMail@yandex.ru";

    public final UserDto userDto = new UserDto(ID_1, NAME_USER_DTO, EMAIL_USER_DTO);

    public final ItemRequestDto itemRequestDto = new ItemRequestDto(ID_1,
            DESCRIPTION_ITEM_REQUEST_DTO,
            userDto,
            null,
            null);


    @Test
    void testGetter() {
        ItemRequestDto itemRequestDto1 = new ItemRequestDto(ID_1,
                DESCRIPTION_ITEM_REQUEST_DTO,
                userDto,
                null,
                null);
        assertSame(itemRequestDto.getId(), itemRequestDto1.getId());
        assertEquals(itemRequestDto.getDescription(), itemRequestDto1.getDescription());
        assertEquals(itemRequestDto.getRequester(), itemRequestDto1.getRequester());
        assertEquals(itemRequestDto.getCreated(), itemRequestDto1.getCreated());
        assertTrue(itemRequestDto.getItems() == null);

    }
}
