package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingDtoByItem;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.common.exception.EntityNotFoundException;
import ru.practicum.shareit.item.comment.mapper.CommentMapper;
import ru.practicum.shareit.item.comment.model.dto.CommentDto;
import ru.practicum.shareit.item.comment.model.dto.CommentDtoResponse;
import ru.practicum.shareit.item.comment.storage.CommentRepository;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemDtoFullResponse;
import ru.practicum.shareit.item.model.dto.ItemDtoUpdate;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.storage.UserRepository;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    private final BookingMapper bookingMapper;

    private final CommentMapper commentMapper;
    private final UserRepository userRepository;

    private final BookingRepository bookingRepository;

    private final CommentRepository commentRepository;

    @Override
    public ItemDto add(ItemDto itemDto, Long userId) {
        Item item = itemMapper.toEntity(itemDto, check(userRepository, userId));
        Item added = itemRepository.save(item);
        log.debug("Added: {} ", added);
        return itemMapper.toDto(added);
    }

    @Override
    public ItemDto update(ItemDtoUpdate itemDtoUpdate, Long itemId, Long userId) {
        Item targetItem = check(itemRepository, itemId);
        if (!targetItem.getOwner().getId().equals(userId)) {
            throw new EntityNotFoundException("Only owner can edit");
        }
        Item updated = itemRepository.save(itemMapper.update(itemDtoUpdate, targetItem));
        log.debug("Updated: {} ", updated);
        return itemMapper.toDto(updated);
    }

    @Override
    public ItemDtoFullResponse findById(Long itemId, Long userId) {
        Item targetItem = check(itemRepository, itemId);
        List<Booking> bookingsByItemId = new ArrayList<>();
        List<CommentDto> commentsByItemId = new ArrayList<>();
        List<CommentDtoResponse> commentDtoResponseList = new ArrayList<>();
        commentRepository.findAll().stream()
                .filter(v -> v.getItem().getId().equals(itemId))
                .forEach(Comment -> commentsByItemId.add(commentMapper.toDto(Comment)));
        commentsByItemId.forEach(CommentDto -> commentDtoResponseList.add(
                commentMapper.toDtoResponse(CommentDto, CommentDto.getAuthor().getName())));
        bookingRepository.findAll().stream()
                .filter(v -> v.getItem().getId().equals(itemId))
                .filter(d -> d.getStart().getDayOfMonth() == (LocalDateTime.now().getDayOfMonth()))
                .filter(s -> s.getStatus().toString().equalsIgnoreCase("APPROVED"))
                .forEach(bookingsByItemId::add);
        if (targetItem.getOwner().getId().equals(userId) && !bookingsByItemId.isEmpty()) {
            Booking lastBooking = bookingsByItemId.stream()
                    .min(Comparator.comparing(Booking::getStart)).get();
            if (bookingsByItemId.size() == 1) {
                BookingDtoByItem last = bookingMapper.toBookingDtoByItem(lastBooking, lastBooking.getBooker().getId());
                return itemMapper.toDtoFullResponse(targetItem, last, null, commentDtoResponseList);
            }
            if (bookingsByItemId.size() == 3) {
                bookingsByItemId.remove(0);
                lastBooking = bookingsByItemId.stream()
                        .min(Comparator.comparing(Booking::getStart)).get();
            }
            Booking nextBooking = bookingsByItemId.stream().max(Comparator.comparing(Booking::getStart)).get();
            BookingDtoByItem last = bookingMapper.toBookingDtoByItem(lastBooking, lastBooking.getBooker().getId());
            BookingDtoByItem next = bookingMapper.toBookingDtoByItem(nextBooking, nextBooking.getBooker().getId());
            return itemMapper.toDtoFullResponse(targetItem, last, next, commentDtoResponseList);
        }
        log.debug("Found: {}", targetItem);
        return itemMapper.toDtoFullResponse(targetItem, null, null, commentDtoResponseList);
    }

    @Override
    public List<ItemDtoFullResponse> findAllByUserId(Long userId) {
        List<Item> foundEntity = itemRepository.findAll();
        List<ItemDtoFullResponse> found = new ArrayList<>();
        foundEntity.stream()
                .filter(v -> v.getOwner().getId().equals(userId))
                .forEach(Item -> found.add(findById(Item.getId(), userId)));
        System.out.println(found);
        return found;
    }

    @Override
    public List<ItemDto> searchByNameOrDescription(String request) {
        List<Item> foundEntity = itemRepository.findAll();
        List<ItemDto> found = new ArrayList<>();
        if (request.isEmpty()) return found;
        foundEntity.stream()
                .filter(v -> v.getName().toLowerCase().contains(request) ||
                        v.getDescription().toLowerCase().contains(request))
                .filter(a -> a.getAvailable())
                .forEach(Item -> found.add(itemMapper.toDto(Item)));
        return found;
    }

    public static <T, I> T check(@NotNull JpaRepository<T, I> storage, I id) throws EntityNotFoundException {
        return storage.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Object not Found"));
    }
}
