package ru.practicum.shareit.booking.model.enums;

import org.junit.jupiter.api.Test;

public class StateTest {

    @Test
    void testToString() {
        System.out.println(State.ALL);
        System.out.println(State.CURRENT);
        System.out.println(State.PAST);
        System.out.println(State.FUTURE);
        System.out.println(State.WAITING);
        System.out.println(State.REJECTED);
    }


}
