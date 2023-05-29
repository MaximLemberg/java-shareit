package ru.practicum.shareit.booking.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingDtoAdd;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {

    private static final String X_SHARER_USER_ID = "X-Sharer-User-Id";

    private final BookingService bookingService;

    @PostMapping
    public BookingDto add(@Valid @RequestBody BookingDtoAdd bookingDto,
                          @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return bookingService.add(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto approve(@RequestHeader(X_SHARER_USER_ID) Long userId,
                              @PathVariable(value = "bookingId") Long bookingId,
                              @RequestParam(value = "approved") Boolean approved) {
        return bookingService.upgrade(bookingId, userId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto findById(@RequestHeader(X_SHARER_USER_ID) Long userId,
                               @RequestParam(value = "state", defaultValue = "ALL") String state,
                               @PathVariable(value = "bookingId") Long bookingId) {
        return bookingService.findById(bookingId, state, userId);
    }

    @GetMapping
    public List<BookingDto> findAllByBookerId(@RequestHeader(X_SHARER_USER_ID) Long bookerId,
                                              @RequestParam(value = "state", defaultValue = "ALL") String state,
                                              @PositiveOrZero @RequestParam(value = "from", defaultValue = "9999") Integer from,
                                              @Positive @RequestParam(value = "size", defaultValue = "9999") Integer size) {
        return bookingService.findAllById(bookerId, state, from, size);
    }

    @GetMapping("/owner")
    public List<BookingDto> findAllByOwnerId(@RequestHeader(X_SHARER_USER_ID) Long ownerId,
                                             @RequestParam(value = "state", defaultValue = "ALL") String state,
                                             @PositiveOrZero @RequestParam(value = "from", defaultValue = "9999") Integer from,
                                             @Positive @RequestParam(value = "size", defaultValue = "9999") Integer size) {
        return bookingService.findAllByOwnerId(ownerId, state, from, size);
    }

}
