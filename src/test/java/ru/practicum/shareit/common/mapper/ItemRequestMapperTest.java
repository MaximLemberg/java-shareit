package ru.practicum.shareit.common.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.dto.ItemRequestDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ItemRequestMapperTest {

    private final ItemRequestMapper itemRequestMapper = Mappers.getMapper(ItemRequestMapper.class);

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

    public final ItemDto itemDto = new ItemDto(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true, 1L);


    public final UserDto userDto = new UserDto(ID_1, NAME_USER, EMAIL_USER);

    public final ItemRequestDto itemRequestDto = new ItemRequestDto(ID_1,
            DESCRIPTION_ITEM_REQUEST,
            userDto,
            null,
            null);

    @Test
    void toDtoTest() {
        ItemRequestDto itemRequestDto1 = itemRequestMapper.toDto(itemRequest);
        assertSame(itemRequest.getId(), itemRequestDto1.getId());
        assertNull(itemRequestMapper.toDto(null));
    }

    @Test
    void toEntity() {
        ItemRequest itemRequest1 = itemRequestMapper.toEntity(itemRequestDto, user, LocalDateTime.now());
        assertSame(itemRequestDto.getId(), itemRequest1.getId());
        assertNull(itemRequestMapper.toEntity(null, null, null));
    }

    @Test
    void toDtoEntityDefault() {
        ItemRequest itemRequest1 = itemRequestMapper.toEntity(itemRequestDto);
        assertSame(itemRequestDto.getId(), itemRequest1.getId());
        assertNull(itemRequestMapper.toEntity(null));
    }

    @Test
    void toUpdate() {
        Set<ItemDto> itemSet = new HashSet<>();
        itemSet.add(itemDto);
        ItemRequest itemRequest1 = itemRequestMapper.update(itemRequestDto, itemRequest);
        assertSame(itemRequestDto.getId(), itemRequest1.getId());
        assertNotNull(itemRequestMapper.update(null, itemRequest));
    }

}
