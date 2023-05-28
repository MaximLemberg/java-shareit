package ru.practicum.shareit.user.model.dto;

import lombok.Value;
import ru.practicum.shareit.common.validate.annotation.EmptyOrNullOrEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Value
public class UserDto {

    Long id;

    @NotEmpty(message = "First name cannot be empty.")
    @Size(max = 64, message = "First name must consist max 64 letters.")
    String name;

    @EmptyOrNullOrEmail(message = "Invalid email format.")
    String email;
}