package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemDtoFullResponse;
import ru.practicum.shareit.item.model.dto.ItemDtoUpdate;

import java.util.List;

public interface ItemService {
    ItemDto add(ItemDto itemDto, Long userId);

    ItemDto update(ItemDtoUpdate itemDto, Long itemId, Long userId);

    ItemDtoFullResponse findById(Long itemId, Long userId);

    List<ItemDtoFullResponse> findAllByUserId(Long userId);

    List<ItemDto> searchByNameOrDescription(String request);
}
