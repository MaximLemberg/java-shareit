package ru.practicum.shareit.item.model.dto;

import lombok.Value;
import ru.practicum.shareit.booking.model.dto.BookingDtoByItem;
import ru.practicum.shareit.item.comment.model.dto.CommentDtoResponse;

import java.util.List;

@Value
public class ItemDtoFullResponse {
    Long id;

    String name;

    String description;

    Boolean available;

    BookingDtoByItem lastBooking;

    BookingDtoByItem nextBooking;

    List<CommentDtoResponse> comments;

}