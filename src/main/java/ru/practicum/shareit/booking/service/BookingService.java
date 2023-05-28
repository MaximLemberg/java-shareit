package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingDtoAdd;

import java.util.List;

public interface BookingService {

    BookingDto add(BookingDtoAdd bookingDto, Long userId);

    BookingDto upgrade(Long bookingId, Long userId, Boolean approved);

    BookingDto findById(Long bookingId, String state, Long userId);

    List<BookingDto> findAllById(Long bookerId, String state, Integer from, Integer size);

    List<BookingDto> findAllByOwnerId(Long bookerId, String state, Integer from, Integer size);
}
