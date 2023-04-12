package ru.practicum.shareit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShareItTests {

	@Test
	void contextLoads() {
	}

	@Test
	void mainTest() {

		final IllegalArgumentException exception = Assertions.assertThrows(
				IllegalArgumentException.class,
				() -> ShareItApp.main(null));

		Assertions.assertEquals("Args must not be null", exception.getMessage());

	}

}
