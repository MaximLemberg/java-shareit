package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.EntityNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoUpdate;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.storage.UserRepository;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    private final UserRepository userRepository;

    @Override
    public ItemDto add(ItemDto itemDto, Long userId) {
        Item item = itemMapper.toEntity(itemDto, check(userRepository, userId));
        Item added = itemRepository.save(item);
        log.debug("Added: {} ", added);
        return itemMapper.toItemDto(added);
    }

    @Override
    public ItemDto update(ItemDtoUpdate itemDto, Long itemId, Long userId) {
        Item targetItem = check(itemRepository, itemId);
        if (!targetItem.getOwner().getId().equals(userId)) {
            throw new EntityNotFoundException("Only owner can edit");
        }
        Item updated = itemRepository.save(itemMapper.toEntity(itemDto, targetItem));
        log.debug("Updated: {} ", updated);
        return itemMapper.toItemDto(updated);
    }

    @Override
    public ItemDto findById(Long itemId, Long userId) {
        Item targetItem = check(itemRepository, itemId);
        log.debug("Found: {}", targetItem);
        return itemMapper.toItemDto(targetItem);
    }

    @Override
    public List<ItemDto> findAllByUserId(Long userId) {
        List<Item> foundEntity = itemRepository.findAll();
        List<ItemDto> found = new ArrayList<>();
        foundEntity.stream()
                .filter(v ->  v.getOwner().getId().equals(userId))
                .forEach(Item -> found.add(itemMapper.toItemDto(Item)));
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
                .forEach(Item -> found.add(itemMapper.toItemDto(Item)));
        return found;
    }

    public static <T, I> T check(@NotNull JpaRepository<T, I> storage, I id) throws EntityNotFoundException {
        return storage.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Object not Found"));
    }
}
