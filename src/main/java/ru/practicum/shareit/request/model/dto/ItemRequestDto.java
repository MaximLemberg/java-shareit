package ru.practicum.shareit.request.model.dto;


import lombok.Value;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.user.model.dto.UserDto;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Value
public class ItemRequestDto {

    Long id;

    @NotBlank
    String description;

    UserDto requester;

    LocalDateTime created;

    Set<ItemDto> items;
}