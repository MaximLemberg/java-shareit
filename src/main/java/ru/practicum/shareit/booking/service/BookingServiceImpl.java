package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingDtoAdd;
import ru.practicum.shareit.booking.model.enums.Status;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.common.exception.EntityNotFoundException;
import ru.practicum.shareit.common.exception.NotAvailableException;
import ru.practicum.shareit.common.exception.UnsupportedStateException;
import ru.practicum.shareit.common.exception.ValidationException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;

    private final UserMapper userMapper;

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    private final UserRepository userRepository;

    @Override
    public BookingDto add(BookingDtoAdd bookingDto, Long userId) {
        checkTime(bookingDto);
        Item checkItem = check(itemRepository, bookingDto.getItemId());
        User checkUser = check(userRepository, userId);
        System.out.println(checkItem);
        if (checkItem.getOwner().getId().equals(userId)) {
            throw new EntityNotFoundException("Item Unavailable");
        }
        if (!checkItem.getAvailable()) {
            throw new NotAvailableException("Item Unavailable");
        }
        Booking booking = bookingMapper.toEntity(bookingDto, itemMapper.toDto(checkItem), userMapper.toDto(checkUser));
        booking.setStatus(Status.WAITING);
        Booking saved = bookingRepository.save(booking);
        log.debug("Added: {} ", saved);
        return bookingMapper.toDto(saved);
    }

    @Override
    public BookingDto upgrade(Long bookingId, Long userId, Boolean approved) {
        Booking booking = check(bookingRepository, bookingId);
        if (!Objects.equals(userId, booking.getItem().getOwner().getId())) {
            throw new EntityNotFoundException("Only owner can change status");
        }
        if (Objects.equals(
                approved ? Status.APPROVED : Status.REJECTED,
                booking.getStatus())) {
            throw new ValidationException("Status already set");
        }
        booking.setStatus(approved ? Status.APPROVED : Status.REJECTED);
        Booking saved = bookingRepository.save(booking);
        return bookingMapper.toDto(saved);
    }

    @Override
    public BookingDto findById(Long bookingId, String state, Long userId) {
        if (state.equalsIgnoreCase("UNSUPPORTED_STATUS")) {
            throw new UnsupportedStateException("Unknown state: UNSUPPORTED_STATUS");
        }
        check(userRepository, userId);
        Booking booking = check(bookingRepository, bookingId);
        if (!Objects.equals(userId, booking.getItem().getOwner().getId())
                && !Objects.equals(userId, booking.getBooker().getId())) {
            throw new EntityNotFoundException("Only the item owner or the booker can view booking");
        }
        return bookingMapper.toDto(booking);
    }

    @Override
    public List<BookingDto> findAllById(Long bookerId, String state, Integer from, Integer size) {
        System.out.println(bookerId);
        System.out.println(state);
        System.out.println(from);
        System.out.println(size);
        if (from >= 0 && size >= 1 && from != 9999 && size != 9999) {
            check(userRepository, bookerId);
            PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by("start").descending());
            System.out.println(bookingRepository.findAllByBookerId(bookerId, pageRequest).stream()
                    .map(bookingMapper::toDto)
                    .collect(Collectors.toList()));
            return bookingRepository.findAllByBookerId(bookerId, pageRequest).stream()
                    .map(bookingMapper::toDto)
                    .collect(Collectors.toList());
        }
        if (from <= 0 || size <= 0) {
            throw new ValidationException("Validation");
        }
        if (state.equalsIgnoreCase("UNSUPPORTED_STATUS")) {
            throw new UnsupportedStateException("Unknown state: UNSUPPORTED_STATUS");
        }
        check(userRepository, bookerId);
        if (state.equalsIgnoreCase("WAITING")) {
            return bookingRepository.findAll().stream()
                    .sorted(Comparator.comparing(Booking::getStart).reversed())
                    .filter(booking -> booking.getBooker().getId().equals(bookerId))
                    .filter(s -> s.getStatus().toString().equalsIgnoreCase("WAITING"))
                    .map(bookingMapper::toDto)
                    .collect(Collectors.toList());
        }
        if (state.equalsIgnoreCase("REJECTED")) {
            return bookingRepository.findAll().stream()
                    .sorted(Comparator.comparing(Booking::getStart).reversed())
                    .filter(booking -> booking.getBooker().getId().equals(bookerId))
                    .filter(s -> s.getStatus().toString().equalsIgnoreCase("REJECTED"))
                    .map(bookingMapper::toDto)
                    .collect(Collectors.toList());

        }
        if (state.equalsIgnoreCase("CURRENT")) {
            return bookingRepository.findAll().stream()
                    .sorted(Comparator.comparing(Booking::getStart).reversed())
                    .filter(booking -> booking.getBooker().getId().equals(bookerId))
                    .filter(t -> LocalDateTime.now().isBefore(t.getEnd()))
                    .filter(t -> LocalDateTime.now().isAfter(t.getStart()))
                    .map(bookingMapper::toDto)
                    .collect(Collectors.toList());
        }
        if (state.equalsIgnoreCase("PAST")) {
            return bookingRepository.findAll().stream()
                    .sorted(Comparator.comparing(Booking::getStart).reversed())
                    .filter(booking -> booking.getBooker().getId().equals(bookerId))
                    .filter(t -> LocalDateTime.now().isAfter(t.getEnd()))
                    .filter(t -> LocalDateTime.now().isAfter(t.getStart()))
                    .map(bookingMapper::toDto)
                    .collect(Collectors.toList());
        }
        return bookingRepository.findAll().stream()
                .sorted(Comparator.comparing(Booking::getStart).reversed())
                .filter(booking -> booking.getBooker().getId().equals(bookerId))
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> findAllByOwnerId(Long ownerId, String state, Integer from, Integer size) {
        if (from >= 0 && size >= 1 && from != 9999 && size != 9999) {
            check(userRepository, ownerId);
            PageRequest pageRequest = PageRequest.of(from, size, Sort.by("start").descending());
            return bookingRepository.findAllByItemOwnerId(ownerId, pageRequest).stream()
                    .map(bookingMapper::toDto)
                    .collect(Collectors.toList());
        }
        if (from <= 0 || size <= 0) {
            throw new ValidationException("Validation");
        }
        if (state.equalsIgnoreCase("UNSUPPORTED_STATUS")) {
            throw new UnsupportedStateException("Unknown state: UNSUPPORTED_STATUS");
        }
        check(userRepository, ownerId);
        if (state.equalsIgnoreCase("WAITING")) {
            return bookingRepository.findAll().stream()
                    .sorted(Comparator.comparing(Booking::getStart).reversed())
                    .filter(booking -> booking.getItem().getOwner().getId().equals(ownerId))
                    .filter(s -> s.getStatus().toString().equalsIgnoreCase("WAITING"))
                    .map(bookingMapper::toDto)
                    .collect(Collectors.toList());
        }
        if (state.equalsIgnoreCase("REJECTED")) {
            return bookingRepository.findAll().stream()
                    .sorted(Comparator.comparing(Booking::getStart).reversed())
                    .filter(booking -> booking.getItem().getOwner().getId().equals(ownerId))
                    .filter(s -> s.getStatus().toString().equalsIgnoreCase("REJECTED"))
                    .map(bookingMapper::toDto)
                    .collect(Collectors.toList());
        }
        if (state.equalsIgnoreCase("CURRENT")) {
            return bookingRepository.findAll().stream()
                    .sorted(Comparator.comparing(Booking::getStart).reversed())
                    .filter(booking -> booking.getItem().getOwner().getId().equals(ownerId))
                    .filter(t -> LocalDateTime.now().isBefore(t.getEnd()))
                    .filter(t -> LocalDateTime.now().isAfter(t.getStart()))
                    .map(bookingMapper::toDto)
                    .collect(Collectors.toList());
        }
        if (state.equalsIgnoreCase("PAST")) {
            return bookingRepository.findAll().stream()
                    .sorted(Comparator.comparing(Booking::getStart).reversed())
                    .filter(booking -> booking.getItem().getOwner().getId().equals(ownerId))
                    .filter(t -> LocalDateTime.now().isAfter(t.getEnd()))
                    .filter(t -> LocalDateTime.now().isAfter(t.getStart()))
                    .map(bookingMapper::toDto)
                    .collect(Collectors.toList());
        }
        return bookingRepository.findAll().stream()
                .sorted(Comparator.comparing(Booking::getStart).reversed())
                .filter(booking -> booking.getItem().getOwner().getId().equals(ownerId))
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    public static <T, I> T check(@NotNull JpaRepository<T, I> storage, I id) throws EntityNotFoundException {
        return storage.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Object not Found"));
    }


    public void checkTime(BookingDtoAdd bookingDto) throws ValidationException {
        if (bookingDto.getStart() == null) {
            throw new ValidationException("Incorrect time");
        }
        if (bookingDto.getEnd() == null) {
            throw new ValidationException("Incorrect time");
        }
        if (bookingDto.getEnd().isBefore(bookingDto.getStart())
                || bookingDto.getEnd().equals(bookingDto.getStart())
                || bookingDto.getStart().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Incorrect time");
        }

    }
}
