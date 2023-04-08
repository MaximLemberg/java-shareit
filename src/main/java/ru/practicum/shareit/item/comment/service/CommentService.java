package ru.practicum.shareit.item.comment.service;

import ru.practicum.shareit.item.comment.model.dto.CommentDto;
import ru.practicum.shareit.item.comment.model.dto.CommentDtoResponse;

public interface CommentService {

    CommentDtoResponse add(CommentDto commentDto, Long itemId, Long userId);

}