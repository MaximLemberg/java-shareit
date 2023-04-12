package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.model.dto.ItemRequestDto;

import java.util.List;


public interface ItemRequestService {

    ItemRequestDto add(ItemRequestDto itemRequestDto, Long userId);

    List<ItemRequestDto> findAllByUserId(Long userId);

    List<ItemRequestDto> findAllByIdWithPage(Long userId, Integer from, Integer size);

    ItemRequestDto findById(Long requestId, Long userId);
}
