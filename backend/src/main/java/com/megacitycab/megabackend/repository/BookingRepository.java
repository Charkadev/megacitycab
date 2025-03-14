package com.megacitycab.megabackend.repository;

import com.megacitycab.megabackend.model.Booking;
import com.megacitycab.megabackend.model.BookingStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findByUserId(String userId);  //  Fetch bookings by User ID
    List<Booking> findByDriverId(String driverId);  //  Fetch bookings by Driver ID
    List<Booking> findByStatus(BookingStatus status); //  Fetch bookings by status
    long countByStatus(BookingStatus status); //  Count bookings by status
}
