package com.megacitycab.megabackend.repository;

import com.megacitycab.megabackend.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findByUserId(String userId);
    List<Booking> findByDriverId(String driverId);
}
