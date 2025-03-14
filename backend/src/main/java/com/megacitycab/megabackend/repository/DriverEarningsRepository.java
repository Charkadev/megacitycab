package com.megacitycab.megabackend.repository;

import com.megacitycab.megabackend.model.DriverEarnings;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface DriverEarningsRepository extends MongoRepository<DriverEarnings, String> {
    List<DriverEarnings> findByDriverId(String driverId); //  Find earnings for a driver
}
