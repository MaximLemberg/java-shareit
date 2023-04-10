package ru.practicum.shareit.item.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.common.exception.EntityNotFoundException;
import ru.practicum.shareit.common.exception.NotAvailableException;
import ru.practicum.shareit.item.comment.mapper.CommentMapper;
import ru.practicum.shareit.item.comment.model.Comment;
import ru.practicum.shareit.item.comment.model.dto.CommentDto;
import ru.practicum.shareit.item.comment.model.dto.CommentDtoResponse;
import ru.practicum.shareit.item.comment.storage.CommentRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final ItemRepository itemRepository;

    private final BookingRepository bookingRepository;

    private final CommentMapper commentMapper;

    private final UserRepository userRepository;

    @Override
    public CommentDtoResponse add(CommentDto commentDto, Long itemId, Long userId) {
        Item targetItem = check(itemRepository, itemId);
        User targetUser = check(userRepository, userId);
        if (bookingRepository.findAll().stream()
                .filter(i -> i.getItem().getId().equals(itemId))
                .filter(b -> b.getBooker().getId().equals(userId))
                .filter(t -> LocalDateTime.now().isAfter(t.getEnd()))
                .noneMatch(s -> s.getStatus().toString().equalsIgnoreCase("APPROVED"))) {
            throw new NotAvailableException("Only completed booking");
        }
        Comment comment = commentMapper.toEntity(commentDto, targetItem, targetUser, LocalDateTime.now());
        Comment added = commentRepository.save(comment);
        log.debug("Added: {} ", added);
        return commentMapper.toDtoResponse(commentMapper.toDto(added), added.getAuthor().getName());
    }

    public static <T, I> T check(@NotNull JpaRepository<T, I> storage, I id) throws EntityNotFoundException {
        return storage.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Object not Found"));
    }
}

