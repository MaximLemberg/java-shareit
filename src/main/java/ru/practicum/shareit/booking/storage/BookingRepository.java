package ru.practicum.shareit.booking.storage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByBookerId(Long bookerId, Pageable pageable);

    List<Booking> findAllByItemOwnerId(Long ownerId, Pageable pageable);

}
