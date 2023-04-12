package ru.practicum.shareit.common.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingDtoAdd;
import ru.practicum.shareit.booking.model.dto.BookingDtoByItem;
import ru.practicum.shareit.booking.model.enums.Status;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemDtoFullResponse;
import ru.practicum.shareit.item.model.dto.ItemDtoUpdate;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemMapperTest {

    private final ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);

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

    public final UserDto userDto = new UserDto(ID_1, NAME_USER, EMAIL_USER);


    public final ItemDto itemDto = new ItemDto(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true, 1L);


    public final BookingDto bookingDto = new BookingDto(ID_1, userDto, itemDto, null, null, Status.APPROVED);

    public final Item item = new Item(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true, itemRequest, user);

    public final ItemDtoUpdate itemDtoUpdate = new ItemDtoUpdate(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true);

    public final Booking booking1 = new Booking(ID_1, user, item, null, null, Status.APPROVED);

    public final BookingDtoAdd bookingDtoAdd = new BookingDtoAdd(ID_1, item.getId(), null, null, Status.APPROVED);

    public final BookingDtoByItem bookingDtoByItem = new BookingDtoByItem(ID_1, user.getId());

    @Test
    void toDtoTest() {
        ItemDto itemDto = itemMapper.toDto(item);
        assertSame(itemDto.getRequestId(), item.getRequest().getId());
    }

    @Test
    void toDtoDefaultTest() {
        ItemDto itemDto = itemMapper.toDto(item);
        assertTrue(item.getId() == itemDto.getId());
        assertTrue(item.getName().equals(itemDto.getName()));
        assertTrue(item.getDescription().equals(itemDto.getDescription()));
    }

    @Test
    void toDtoEntityDefault() {
        Item item = itemMapper.toEntity(itemDto);
        assertTrue(item.getId() == itemDto.getId());
        assertTrue(item.getName().equals(itemDto.getName()));
        assertTrue(item.getDescription().equals(itemDto.getDescription()));
    }

    @Test
    void toDtoEntity() {
        Item item = itemMapper.toEntity(itemDto, user);
        assertTrue(item.getId() == itemDto.getId());
        assertTrue(item.getName().equals(itemDto.getName()));
        assertTrue(item.getDescription().equals(itemDto.getDescription()));
    }

    @Test
    void toUpdate() {
        Item item1 = itemMapper.update(itemDto, item);
        assertTrue(item1.getId() == item.getId());
        assertTrue(item1.getName().equals(item.getName()));
        assertTrue(item1.getDescription().equals(item.getDescription()));
    }

    @Test
    void toDtoFullResponse() {
        ItemDtoFullResponse itemDtoFullResponse = itemMapper.toDtoFullResponse(
                item,
                bookingDtoByItem, bookingDtoByItem, null);
        assertTrue(itemDtoFullResponse.getId() == item.getId());
    }

    @Test
    void getItemRequest() {
        ItemRequest itemRequest = itemMapper.getItemRequest(1L);
        assertTrue(itemRequest.getId() == 1L);
    }

}
