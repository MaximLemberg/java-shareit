package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.exception.EntityNotFoundException;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.dto.UserDto;
import ru.practicum.shareit.user.storage.UserRepository;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDto add(UserDto userDto) {
        User added = userRepository.save(userMapper.toEntity(userDto));
        log.debug("Added: {} ", added);
        return userMapper.toDto(added);
    }

    @Override
    public UserDto update(UserDto userDto, Long userId) {
        User entity = userMapper.update(userDto, userRepository.getReferenceById(userId));
        User updated = userRepository.save(entity);
        log.debug("Updated: {} ", updated);
        return userMapper.toDto(updated);
    }

    @Override
    public UserDto findById(Long userId) {
        User found = check(userRepository, userId);
        log.debug("Found: {}", found);
        return userMapper.toDto(found);
    }

    @Override
    public UserDto delete(Long userId) {
        User deleted = userRepository.getReferenceById(userId);
        userRepository.delete(deleted);
        log.debug("Deleted: {}: ", deleted);
        return userMapper.toDto(deleted);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> foundEntity = userRepository.findAll();
        List<UserDto> found = new ArrayList<>();
        foundEntity.forEach(User -> found.add(userMapper.toDto(User)));
        return found;
    }

    public static <T, I> T check(@NotNull JpaRepository<T, I> storage, I id) throws EntityNotFoundException {
        return storage.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Object not Found"));
    }

}
