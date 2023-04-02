package ru.practicum.shareit.item.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoUpdate;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private static final String X_SHARER_USER_ID = "X-Sharer-User-Id";

    private final ItemService itemService;

    @PostMapping
    public ItemDto add(@Valid @RequestBody ItemDto itemDto,
                       @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemService.add(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@Valid @RequestBody ItemDtoUpdate itemDto,
                          @PathVariable(value = "itemId") Long itemId,
                          @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemService.update(itemDto, itemId, userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto findById(@PathVariable(value = "itemId") Long itemId,
                                    @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemService.findById(itemId, userId);
    }

    @GetMapping
    public List<ItemDto> findAllByUserId(@RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemService.findAllByUserId(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchByNameOrDescription(@RequestParam(value = "text") String request) {
        return itemService.searchByNameOrDescription(request.toLowerCase());
    }
}
