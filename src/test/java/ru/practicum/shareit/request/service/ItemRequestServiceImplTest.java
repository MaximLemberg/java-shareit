package ru.practicum.shareit.request.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.common.exception.EntityNotFoundException;
import ru.practicum.shareit.common.exception.ValidationException;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.dto.ItemRequestDto;
import ru.practicum.shareit.request.storage.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemRequestServiceImplTest {

    @Mock
    private ItemRequestRepository requestRepository;
    @Mock
    private UserRepository userRepository;

    private final ItemRequestMapper itemRequestMapper = Mappers.getMapper(ItemRequestMapper.class);

    private ItemRequestServiceImpl requestService;

    public static final long ID_1 = 1L;


    public static final String NAME_USER = "testUserName";

    public static final String EMAIL_USER = "testUserEMail@yandex.ru";

    public static final String DESCRIPTION_ITEM_REQUEST_DTO = "testItemRequestDtoDescription";

    public static final String DESCRIPTION_ITEM_REQUEST = "testItemRequestDescription";

    public final UserDto userDto1 = new UserDto(ID_1, NAME_USER, EMAIL_USER);
    private final User user1 = new User(ID_1, NAME_USER, EMAIL_USER);
    private final ItemRequestDto itemRequestDto1 = new ItemRequestDto(ID_1,
            DESCRIPTION_ITEM_REQUEST_DTO,
            userDto1,
            null,
            null);

    private final ItemRequestDto itemRequestDto2 = new ItemRequestDto(2L,
            DESCRIPTION_ITEM_REQUEST_DTO,
            userDto1,
            null,
            null);
    private final ItemRequest itemRequest1 = new ItemRequest(ID_1,
            DESCRIPTION_ITEM_REQUEST,
            user1,
            null,
            null);

    private final ItemRequest itemRequest2 = new ItemRequest(2L,
            DESCRIPTION_ITEM_REQUEST,
            user1,
            null,
            null);

    @BeforeEach
    void setUp() {
        requestService = new ItemRequestServiceImpl(
                requestRepository,
                userRepository,
                itemRequestMapper
        );
    }

    @Test
    void addItemRequestTest() {
        when(requestRepository.save(any())).thenAnswer(invocationOnMock -> {
            ItemRequest request = invocationOnMock.getArgument(0, ItemRequest.class);
            request.setCreated(null);
            return request;
        });
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));

        ItemRequestDto added = requestService.add(itemRequestDto1, user1.getId());

        assertEquals(itemRequestDto1, added);
        verify(requestRepository, times(1)).save(itemRequest1);
    }

    @Test
    void findAllByUserIdTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(requestRepository.findAll()).thenReturn(List.of(itemRequest1, itemRequest2));

        List<ItemRequestDto> found = requestService.findAllByUserId(user1.getId());

        assertNotNull(found);
        assertEquals(2, found.size());
    }

    @Test
    void findByUserIdTest() {
        when(requestRepository.getReferenceById(anyLong())).thenReturn(itemRequest1);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(requestRepository.findById(anyLong())).thenReturn(Optional.of(itemRequest1));

        ItemRequestDto found = requestService.findById(itemRequest1.getId(), user1.getId());

        assertNotNull(found);
        assertEquals(itemRequest1, itemRequestMapper.toEntity(found));
    }

    @Test
    void findAllByIdWithPageTest() {
        final ItemRequest itemRequest2 = new ItemRequest(2L,
                DESCRIPTION_ITEM_REQUEST,
                user1,
                null,
                null);
        when(requestRepository.findAllByRequesterIdNot(anyLong(), any())).thenReturn(List.of(itemRequest1, itemRequest2));

        List<ItemRequestDto> found = requestService.findAllByIdWithPage(user1.getId(), 1, 1);

        final ValidationException exception = Assertions.assertThrows(
                ValidationException.class,
                () -> requestService.findAllByIdWithPage(user1.getId(), -1, 1));

        assertNotNull(found);
        assertEquals(2, found.size());
        Assertions.assertEquals("From negative", exception.getMessage());
        verify(requestRepository, times(1)).findAllByRequesterIdNot(user1.getId(),
                PageRequest.of(1, 1, Sort.by("created").descending()));
    }

    @Test
    void checkMethodUserTest() {
        final EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> requestService.check(requestRepository, 100L));

        Assertions.assertEquals("Object not Found", exception.getMessage());
    }


}