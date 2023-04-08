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
import ru.practicum.shareit.user.model.User;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface ItemMapper extends Mapper<Item, ItemDto> {

    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "name", source = "source.name")
    @Mapping(target = "owner", source = "owner")
    Item toEntity(ItemDto source, User owner);


    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "name", source = "source.name")
    @Mapping(target = "description", source = "source.description")
    @Mapping(target = "available", source = "source.available")
    @Mapping(target = "lastBooking", source = "last")
    @Mapping(target = "nextBooking", source = "next")
    @Mapping(target = "comments", source = "comments")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ItemDtoFullResponse toDtoFullResponse(Item source, BookingDtoByItem last, BookingDtoByItem next,
                                          List<CommentDtoResponse> comments);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Item update(ItemDtoUpdate source, @MappingTarget Item target);
}
