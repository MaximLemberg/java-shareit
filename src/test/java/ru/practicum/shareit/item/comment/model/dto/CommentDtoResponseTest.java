package ru.practicum.shareit.item.comment.model.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.comment.model.dto.CommentDtoResponse;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.user.model.dto.UserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class CommentDtoResponseTest {

    public static final long ID_1 = 1L;

    public static final String COMMENT_TEXT = "COMMENT_TEXT";

    public static final String ITEM_DESCRIPTION = "testItemDescription";


    public static final String ITEM_NAME = "testItemName";

    public static final String NAME_USER_DTO = "testUserDtoName";

    public static final String EMAIL_USER_DTO = "testUserDtoEMail@yandex.ru";

    public final UserDto userDto = new UserDto(ID_1, NAME_USER_DTO, EMAIL_USER_DTO);

    public final ItemDto itemDto = new ItemDto(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true, 1L);

    public final CommentDtoResponse commentDtoResponse = new CommentDtoResponse(
            ID_1,
            COMMENT_TEXT,
            userDto.getName(),
            null);

    @Test
    void testGetter() {
        CommentDtoResponse newCommentDtoResponse = new CommentDtoResponse(commentDtoResponse.getId(),
                commentDtoResponse.getText(),
                commentDtoResponse.getAuthorName(),
                commentDtoResponse.getCreated());
        assertSame(commentDtoResponse.getId(), newCommentDtoResponse.getId());
        assertEquals(commentDtoResponse.getText(), newCommentDtoResponse.getText());
        assertEquals(commentDtoResponse.getAuthorName(), newCommentDtoResponse.getAuthorName());
        assertEquals(commentDtoResponse.getCreated(), newCommentDtoResponse.getCreated());
    }
}
