package ru.practicum.shareit.request.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.model.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private static final String X_SHARER_USER_ID = "X-Sharer-User-Id";

    private final ItemRequestService requestService;

    @PostMapping
    public ItemRequestDto add(@Valid @RequestBody ItemRequestDto itemRequestDto,
                              @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return requestService.add(itemRequestDto, userId);
    }

    @GetMapping
    public List<ItemRequestDto> findAllByUserId(@RequestHeader(X_SHARER_USER_ID) Long userId) {
        return requestService.findAllByUserId(userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto findById(@PathVariable(value = "requestId") Long requestId,
                                   @RequestHeader(X_SHARER_USER_ID) Long userId) {
        return requestService.findById(requestId, userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> findAllByIdWithPage(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                                    @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") Integer from,
                                                    @Positive @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return requestService.findAllByIdWithPage(userId, from, size);
    }
}
