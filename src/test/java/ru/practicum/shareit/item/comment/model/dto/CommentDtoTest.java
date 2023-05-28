package ru.practicum.shareit.item.comment.model.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.user.model.dto.UserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class CommentDtoTest {
    public static final long ID_1 = 1L;

    public static final String COMMENT_TEXT = "COMMENT_TEXT";

    public static final String ITEM_DESCRIPTION = "testItemDescription";


    public static final String ITEM_NAME = "testItemName";

    public static final String NAME_USER_DTO = "testUserDtoName";

    public static final String EMAIL_USER_DTO = "testUserDtoEMail@yandex.ru";

    public final UserDto userDto = new UserDto(ID_1, NAME_USER_DTO, EMAIL_USER_DTO);

    public final ItemDto itemDto = new ItemDto(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true, 1L);

    public final CommentDto commentDto = new CommentDto(ID_1, COMMENT_TEXT, itemDto, userDto, null);

    @Test
    void testGetter() {
        CommentDto newCommentDto = new CommentDto(commentDto.getId(),
                commentDto.getText(),
                commentDto.getItem(),
                commentDto.getAuthor(),
                commentDto.getCreated());
        assertSame(commentDto.getId(), newCommentDto.getId());
        assertEquals(commentDto.getText(), newCommentDto.getText());
        assertEquals(commentDto.getItem(), newCommentDto.getItem());
        assertEquals(commentDto.getAuthor(), newCommentDto.getAuthor());
        assertEquals(commentDto.getCreated(), newCommentDto.getCreated());
    }
}
