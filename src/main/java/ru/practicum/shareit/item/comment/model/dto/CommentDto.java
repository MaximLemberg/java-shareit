package ru.practicum.shareit.item.comment.model.dto;

import lombok.Value;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.user.model.dto.UserDto;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Value
public class CommentDto {

    Long id;

    @NotEmpty
    String text;

    ItemDto item;

    UserDto author;

    LocalDateTime created;
}