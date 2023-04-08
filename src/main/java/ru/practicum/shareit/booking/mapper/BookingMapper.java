package ru.practicum.shareit.booking.mapper;

import org.mapstruct.Mapping;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.dto.BookingDto;
import ru.practicum.shareit.booking.model.dto.BookingDtoAdd;
import ru.practicum.shareit.booking.model.dto.BookingDtoByItem;
import ru.practicum.shareit.common.mapper.Mapper;
import ru.practicum.shareit.item.model.dto.ItemDto;
import ru.practicum.shareit.user.model.dto.UserDto;

@org.mapstruct.Mapper(componentModel = "spring")
public interface BookingMapper extends Mapper<Booking, BookingDto> {

    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "item", source = "item")
    @Mapping(target = "booker", source = "booker")
    @Mapping(target = "start", source = "source.start")
    @Mapping(target = "end", source = "source.end")
    Booking toEntity(BookingDtoAdd source, ItemDto item, UserDto booker);


    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "bookerId", source = "bookerId")
    BookingDtoByItem toBookingDtoByItem(Booking source, Long bookerId);

}
