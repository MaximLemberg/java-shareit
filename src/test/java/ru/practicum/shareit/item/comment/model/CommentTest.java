package ru.practicum.shareit.item.comment.model;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class CommentTest {

    public static final long ID_1 = 1L;

    public static final String COMMENT_TEXT = "COMMENT_TEXT";

    public static final String ITEM_DESCRIPTION = "testItemDescription";

    public static final String DESCRIPTION_ITEM_REQUEST = "testItemRequestDescription";

    public static final String EMAIL_USER = "testUserEMail@yandex.ru";

    public static final String NAME_USER = "testUserName";

    public final User user = new User(ID_1, NAME_USER, EMAIL_USER);

    public static final String ITEM_NAME = "testItemName";


    public final ItemRequest itemRequest = new ItemRequest(ID_1,
            DESCRIPTION_ITEM_REQUEST,
            user,
            LocalDateTime.now(),
            null);

    public final Item item = new Item(ID_1, ITEM_NAME, ITEM_DESCRIPTION, true, itemRequest, user);

    public final Comment comment = new Comment(ID_1, COMMENT_TEXT, item, user, null);

    @Test
    void testToString() {
        System.out.println(comment);
    }

    @Test
    void testGetterAndSetter() {
        Comment newComment = new Comment(comment.getId(),
                comment.getText(),
                comment.getItem(),
                comment.getAuthor(),
                comment.getCreated());
        newComment.setId(comment.getId());
        newComment.setText(comment.getText());
        newComment.setItem(comment.getItem());
        newComment.setAuthor(comment.getAuthor());
        newComment.setCreated(comment.getCreated());
        assertSame(comment.getId(), comment.getId());
        assertEquals(comment.getText(), comment.getText());
        assertEquals(comment.getItem(), comment.getItem());
        assertEquals(comment.getAuthor(), comment.getAuthor());
        assertEquals(comment.getCreated(), comment.getCreated());
    }

    @Test
    void testEquals() {
        Comment comment = new Comment();
        Comment comment2 = new Comment();
        assertFalse(comment.equals(null));
        assertTrue(comment.equals(comment));
        assertFalse(comment.equals(comment2));
    }

    @Test
    void testHashCode() {
        Comment comment = new Comment();
        int code = comment.hashCode();
        assertEquals(comment.getClass().hashCode(), code);
    }
}
