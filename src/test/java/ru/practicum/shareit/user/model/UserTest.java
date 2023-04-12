package ru.practicum.shareit.user.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    public static final long ID_1 = 1L;

    public static final String NAME_USER = "testUserName";

    public static final String EMAIL_USER = "testUserEMail@yandex.ru";

    public final User user = new User(ID_1, NAME_USER, EMAIL_USER);

    @Test
    void testToString() {
        System.out.println(user);
    }

    @Test
    void testGetterAndSetter() {
        User newUser = new User(user.getId(), user.getName(), user.getEmail());
        newUser.setId(user.getId());
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        assertTrue(user.getId() == newUser.getId());
        assertTrue(user.getName().equals(newUser.getName()));
        assertTrue(user.getEmail().equals(newUser.getEmail()));
    }

    @Test
    void testEquals() {
        User user = new User();
        User user2 = new User();
        assertFalse(user.equals(null));
        assertTrue(user.equals(user));
        assertFalse(user.equals(user2));
    }

    @Test
    void testHashCode() {
        User user = new User();
        int code = user.hashCode();
        assertEquals(user.getClass().hashCode(), code);
    }
}