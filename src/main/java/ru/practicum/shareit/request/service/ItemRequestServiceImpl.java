package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.exception.EntityNotFoundException;
import ru.practicum.shareit.common.exception.ValidationException;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.dto.ItemRequestDto;
import ru.practicum.shareit.request.storage.ItemRequestRepository;
import ru.practicum.shareit.user.storage.UserRepository;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository itemRequestRepository;

    private final UserRepository userRepository;

    private final ItemRequestMapper itemRequestMapper;

    @Override
    public ItemRequestDto add(ItemRequestDto itemRequestDto, Long userId) {
        ItemRequest itemRequest = itemRequestMapper.toEntity(itemRequestDto, check(userRepository, userId),
                LocalDateTime.now());
        ItemRequest added = itemRequestRepository.save(itemRequest);
        log.debug("Added: {} ", added);
        return itemRequestMapper.toDto(added);
    }

    @Override
    public List<ItemRequestDto> findAllByUserId(Long userId) {
        check(userRepository, userId);
        return itemRequestRepository.findAllByRequesterId(userId).stream()
                .filter(v -> v.getRequester().getId().equals(userId))
                .map(itemRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemRequestDto findById(Long requestId, Long userId) {
        check(userRepository, userId);
        check(itemRequestRepository, requestId);
        return itemRequestMapper.toDto(itemRequestRepository.getReferenceById(requestId));
    }

    @Override
    public List<ItemRequestDto> findAllByIdWithPage(Long userId, Integer from, Integer size) {
        if (from < 0) {
            throw new ValidationException("From negative");
        }
        PageRequest pageRequest = PageRequest.of(from, size, Sort.by("created").descending());
        return itemRequestRepository.findAllByRequesterIdNot(userId, pageRequest).stream()
                .map(itemRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    public static <T, I> T check(@NotNull JpaRepository<T, I> storage, I id) throws EntityNotFoundException {
        return storage.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Object not Found"));
    }
}
