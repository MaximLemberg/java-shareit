package ru.practicum.shareit.common.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.item.comment.mapper.CommentMapper;
import ru.practicum.shareit.item.comment.model.Comment;
import ru.practicum.shareit.item.comment.model.dto.CommentDto;
import ru.practicum.shareit.item.comment.model.dto.CommentDtoResponse;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class CommentMapperTest {

    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    public static final long ID_1 = 1L;

    public static final String COMMENT_TEXT = "COMMENT_TEXT";

    public static final String ITEM_DESCRIPTION = "testItemDescription";

    public static final String DESCRIPTION_ITEM_REQUEST = "testItemRequestDescription";

    public static final String EMAIL_USER = "testUserEMail@yandex.ru";

    public static final String NAME_USER = "testUserName";

    public final User user = new User(ID_1, NAME_USER, EMAIL_USER);

    public static final String ITEM_NAME = "testItemName";

    public static final String NAME_USER_DTO = "testUserDtoName";

    public static final String EMAIL_USER_DTO = "testUserDtoEMail@yandex.ru";

    public final UserDto userDto = new UserDto(ID_1, NAME_USER_DTO, EMAIL_USER_DTO);

    public final ItemDto itemDto = new ItemDto(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true, 1L);

    public final CommentDto commentDto = new CommentDto(ID_1, COMMENT_TEXT, itemDto, userDto, null);


    public final ItemRequest itemRequest = new ItemRequest(ID_1,
            DESCRIPTION_ITEM_REQUEST,
            user,
            LocalDateTime.now(),
            null);

    public final Item item = new Item(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true, itemRequest, user);

    public final Comment comment = new Comment(ID_1, COMMENT_TEXT, item, user, null);

    @Test
    void toDtoTest() {
        CommentDto commentDto1 = commentMapper.toDto(comment);
        assertSame(comment.getId(), commentDto1.getId());
        assertNull(commentMapper.toDto(null));
    }

    @Test
    void toDtoEntity() {
        Comment comment1 = commentMapper.toEntity(commentDto);
        assertSame(commentDto.getId(), comment1.getId());
        assertNull(commentMapper.toEntity(null));
    }

    @Test
    void toDtoEntityDefault() {
        Comment comment1 = commentMapper.toEntity(commentDto, item, user, null);
        assertSame(commentDto.getId(), comment1.getId());
        assertNull(commentMapper.toEntity(null, null, null, null));
    }

    @Test
    void toUpdate() {
        Comment comment1 = commentMapper.update(commentDto, comment);
        assertSame(comment1.getId(), comment.getId());
        assertNull(commentMapper.update(null, null));
    }

    @Test
    void toDtoResponse() {
        CommentDtoResponse comment1 = commentMapper.toDtoResponse(commentDto, "comment");
        assertSame(comment1.getId(), commentDto.getId());
        assertNull(commentMapper.toDtoResponse(null, null));
    }


}
