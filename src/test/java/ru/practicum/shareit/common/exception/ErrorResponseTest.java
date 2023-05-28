package ru.practicum.shareit.common.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ErrorResponseTest {

    @Test
    void testGetter() {
        ErrorResponse errorResponse = new ErrorResponse(417, "HelloWorld");
        assertEquals(errorResponse.getTimestamp(), errorResponse.getTimestamp());
        assertSame(errorResponse.getCode(), errorResponse.getCode());
        assertEquals(errorResponse.getError(), errorResponse.getError());
        assertEquals(errorResponse.hashCode(), errorResponse.hashCode());
        System.out.println(errorResponse);
    }
}
