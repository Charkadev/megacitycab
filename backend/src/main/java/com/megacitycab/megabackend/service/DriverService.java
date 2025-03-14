package com.megacitycab.megabackend.service;

import com.megacitycab.megabackend.model.Driver;
import com.megacitycab.megabackend.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;

    //  Add a new driver
    public Driver addDriver(Driver driver) {
        driver.setAvailability(true); //  New drivers start as available
        return driverRepository.save(driver);
    }

    //  Assign a car to a driver
    public Driver assignCarToDriver(String driverId, String carId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        driver.setAssignedCarId(carId);
        driver.setAvailability(true); //  Driver is now available with an assigned car
        return driverRepository.save(driver);
    }

    //  Fetch all drivers
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    //  Fetch only available drivers
    public List<Driver> getAvailableDrivers() {
        return driverRepository.findByAvailabilityTrue();
    }

    //  Find an available driver assigned to a specific car
    public Optional<Driver> getAvailableDriverForCar(String carId) {
        return driverRepository.findByAssignedCarIdAndAvailabilityTrue(carId);
    }
}
