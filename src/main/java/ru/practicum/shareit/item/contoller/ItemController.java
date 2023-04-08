package ru.practicum.shareit.item.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.comment.model.dto.CommentDto;
import ru.practicum.shareit.item.comment.model.dto.CommentDtoResponse;
import ru.practicum.shareit.item.comment.service.CommentService;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.item.model.dto.ItemDtoFullResponse;
import ru.practicum.shareit.item.model.dto.ItemDtoUpdate;
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

    private final CommentService commentService;

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
    public ItemDtoFullResponse findById(@PathVariable(value = "itemId") Long itemId,
                                        @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemService.findById(itemId, userId);
    }

    @GetMapping
    public List<ItemDtoFullResponse> findAllByUserId(@RequestHeader(X_SHARER_USER_ID) Long userId) {
        return itemService.findAllByUserId(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchByNameOrDescription(@RequestParam(value = "text") String request) {
        return itemService.searchByNameOrDescription(request.toLowerCase());
    }

    @PostMapping("/{itemId}/comment")
    public CommentDtoResponse addComment(@Valid @RequestBody CommentDto commentDto,
                                         @PathVariable(value = "itemId") Long itemId,
                                         @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return commentService.add(commentDto, itemId, userId);
    }
}
