package ru.practicum.shareit.user.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.validate.annotation.EmptyOrNullOrEmail;
import javax.validation.constraints.*;


@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDto {

    Long id;

    @NotEmpty(message = "First name cannot be empty.")
    @Size(max = 64, message = "First name must consist max 64 letters.")
    String name;

    @EmptyOrNullOrEmail(message = "Invalid email format.")
    String email;
}