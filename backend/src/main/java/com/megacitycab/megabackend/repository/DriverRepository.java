package com.megacitycab.megabackend.repository;

import com.megacitycab.megabackend.model.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends MongoRepository<Driver, String> {
    Optional<Driver> findByAssignedCarIdAndAvailabilityTrue(String assignedCarId);
    List<Driver> findByAvailabilityTrue();
}
