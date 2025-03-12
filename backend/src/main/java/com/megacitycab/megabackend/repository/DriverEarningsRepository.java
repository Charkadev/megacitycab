package com.megacitycab.megabackend.repository;

import com.megacitycab.megabackend.model.DriverEarnings;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface DriverEarningsRepository extends MongoRepository<DriverEarnings, String> {
    Optional<DriverEarnings> findByDriverId(String driverId);
}
