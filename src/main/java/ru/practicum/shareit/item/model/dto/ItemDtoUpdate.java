package ru.practicum.shareit.item.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.validation.constraints.Size;


@Value
public class ItemDtoUpdate {

    Long id;

    @Size(max = 64)
    String name;


    @Size(max = 256)
    String description;


    Boolean available;

}