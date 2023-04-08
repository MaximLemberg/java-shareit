package ru.practicum.shareit.booking.model.dto;

import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDtoByItem {

    Long id;

    Long bookerId;
}