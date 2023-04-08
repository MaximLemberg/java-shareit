package ru.practicum.shareit.booking.model.dto;


import lombok.Value;
import ru.practicum.shareit.booking.model.enums.Status;

import java.time.LocalDateTime;

@Value
public class BookingDtoAdd {

    Long id;

    Long itemId;

    LocalDateTime start;

    LocalDateTime end;

    Status status;
}