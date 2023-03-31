package ru.practicum.shareit.item.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * TODO Sprint add-controllers.
 */
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ItemDto {

    Long id;

    @NotNull
    @NotEmpty
    @Size(max = 64)
    String name;

    @NotNull
    @NotEmpty
    @Size(max = 256)
    String description;

    @NotNull
    Boolean available;

}