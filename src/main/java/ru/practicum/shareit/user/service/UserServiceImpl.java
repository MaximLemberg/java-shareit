package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;
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
        User added = userRepository.save(userMapper.toEntity(userDto, checkEmail(userDto.getEmail())));
        log.debug("Added: {} ", added);
        return userMapper.toUserDto(added);
    }

    @Override
    public UserDto update(UserDto userDto, Long userId) {
        User entity = userMapper.toEntity(userDto, userRepository.getReferenceById(userId));
        User updated = userRepository.save(entity);
        log.debug("Updated: {} ", updated);
        return userMapper.toUserDto(updated);
    }

    @Override
    public UserDto findById(Long userId) {
        User found = userRepository.getReferenceById(userId);
        log.debug("Found: {}", found);
        return userMapper.toUserDto(found);
    }

    @Override
    public UserDto delete(Long userId) {
        User deleted = userRepository.getReferenceById(userId);
        userRepository.delete(deleted);
        log.debug("Deleted: {}: ", deleted);
        return userMapper.toUserDto(deleted);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> foundEntity = userRepository.findAll();
        List<UserDto> found = new ArrayList<>();
        foundEntity.forEach(User -> found.add(userMapper.toUserDto(User)));
        return found;
    }

    public boolean checkEmail(String email) {
        return findAll().stream().map(UserDto::getEmail).filter(email::equals).findFirst().isPresent();
    }
}
