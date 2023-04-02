package ru.practicum.shareit.item.mapper;


import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoUpdate;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@Component
public class ItemMapper {


    public Item toEntity(ItemDto itemDto, User user) {
            return new Item(0L,
                    itemDto.getName(),
                    itemDto.getDescription(),
                    itemDto.getAvailable(),
                    user
            );
    }

    public Item toEntity(ItemDtoUpdate itemDto, Item targetItem) {
        if (itemDto.getName() == null) {
            targetItem.setName(targetItem.getName());
        } else targetItem.setName(itemDto.getName());
        if (itemDto.getDescription() == null)  {
            targetItem.setDescription(targetItem.getDescription());
        } else targetItem.setDescription(itemDto.getDescription());
        if (itemDto.getAvailable() == null)  {
            targetItem.setAvailable(targetItem.getAvailable());
        } else targetItem.setAvailable(itemDto.getAvailable());
        return targetItem;
    }

    public ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable()
        );
    }
}
