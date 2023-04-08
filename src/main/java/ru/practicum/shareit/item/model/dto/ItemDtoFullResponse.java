package ru.practicum.shareit.item.model.dto;

import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.model.dto.BookingDtoByItem;
import ru.practicum.shareit.item.comment.model.dto.CommentDtoResponse;

import java.util.List;

@Value
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDtoFullResponse {
    Long id;

    String name;

    String description;

    Boolean available;

    BookingDtoByItem lastBooking;

    BookingDtoByItem nextBooking;

    List<CommentDtoResponse> comments;

}