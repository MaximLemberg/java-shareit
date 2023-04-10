package ru.practicum.shareit.item.comment.mapper;

import org.mapstruct.Mapping;
import ru.practicum.shareit.common.mapper.Mapper;
import ru.practicum.shareit.item.comment.model.Comment;
import ru.practicum.shareit.item.comment.model.dto.CommentDto;
import ru.practicum.shareit.item.comment.model.dto.CommentDtoResponse;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@org.mapstruct.Mapper(componentModel = "spring")
public interface CommentMapper extends Mapper<Comment, CommentDto> {

    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "text", source = "source.text")
    @Mapping(target = "item", source = "item")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "created", source = "created")
    Comment toEntity(CommentDto source, Item item, User author, LocalDateTime created);

    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "text", source = "source.text")
    @Mapping(target = "authorName", source = "authorName")
    @Mapping(target = "created", source = "source.created")
    CommentDtoResponse toDtoResponse(CommentDto source, String authorName);

}
