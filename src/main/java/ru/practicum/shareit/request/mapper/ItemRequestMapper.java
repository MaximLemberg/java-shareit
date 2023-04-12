package ru.practicum.shareit.request.mapper;

import org.mapstruct.Mapping;
import ru.practicum.shareit.common.mapper.Mapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.dto.ItemRequestDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@org.mapstruct.Mapper(componentModel = "spring", uses = {ItemMapper.class})
public interface ItemRequestMapper extends Mapper<ItemRequest, ItemRequestDto> {

    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "requester", source = "user")
    @Mapping(target = "created", source = "created")
    ItemRequest toEntity(ItemRequestDto source, User user, LocalDateTime created);
}