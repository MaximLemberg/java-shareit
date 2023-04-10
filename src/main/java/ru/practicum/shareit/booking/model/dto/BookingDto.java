package ru.practicum.shareit.booking.model.dto;


import lombok.Value;
import ru.practicum.shareit.booking.model.enums.Status;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.time.LocalDateTime;

@Value
public class BookingDto {

    Long id;

    UserDto booker;

    ItemDto item;

    LocalDateTime start;

    LocalDateTime end;

    Status status;
}