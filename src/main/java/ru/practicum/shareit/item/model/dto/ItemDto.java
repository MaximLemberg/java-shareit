package ru.practicum.shareit.item.model.dto;

import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Value
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

    Long requestId;

}