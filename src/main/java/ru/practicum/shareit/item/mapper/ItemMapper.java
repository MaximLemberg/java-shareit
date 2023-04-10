package ru.practicum.shareit.item.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.shareit.booking.model.dto.BookingDtoByItem;
import ru.practicum.shareit.common.mapper.Mapper;
import ru.practicum.shareit.item.comment.model.dto.CommentDtoResponse;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemDtoFullResponse;
import ru.practicum.shareit.item.model.dto.ItemDtoUpdate;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface ItemMapper extends Mapper<Item, ItemDto> {

    @Override
    @Mapping(target = "requestId", source = "entity.request.id")
    ItemDto toDto(Item entity);

    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "name", source = "source.name")
    @Mapping(target = "request", source = "source.requestId")
    Item toEntity(ItemDto source, User owner);


    ItemRequest getItemRequest(Long id);

    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "lastBooking", source = "last")
    @Mapping(target = "nextBooking", source = "next")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ItemDtoFullResponse toDtoFullResponse(Item source, BookingDtoByItem last, BookingDtoByItem next,
                                          List<CommentDtoResponse> comments);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Item update(ItemDtoUpdate source, @MappingTarget Item target);
}
