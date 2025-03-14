package com.megacitycab.megabackend.service;

import com.megacitycab.megabackend.model.Car;
import com.megacitycab.megabackend.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private static final Logger logger = LoggerFactory.getLogger(CarService.class);

    public List<Car> getAllCars() {
        List<Car> cars = carRepository.findAll();
        logger.info("🚗 Retrieved All Cars: {}", cars);
        return cars;
    }

    public List<Car> getAvailableCars() {
        List<Car> availableCars = carRepository.findByStatus("Available"); // ✅ Now filtering by String
        logger.info(" Retrieved Available Cars: {}", availableCars);
        return availableCars;
    }

    public Car addCar(Car car) {
        try {
            //  Ensure status is properly formatted
            String carStatus = car.getStatus().toLowerCase(); // Convert to lowercase for consistency
            if (!carStatus.equals("available") && !carStatus.equals("maintenance") && !carStatus.equals("assigned")) {
                throw new IllegalArgumentException(" Invalid car status! Allowed values: Available, Maintenance, Assigned");
            }

            car.setStatus(carStatus.substring(0, 1).toUpperCase() + carStatus.substring(1)); // Capitalize first letter
            Car savedCar = carRepository.save(car);
            logger.info("✅ Car Added: {}", savedCar);
            return savedCar;
        } catch (IllegalArgumentException e) {
            logger.error(" Error: Invalid Car Status - {}", car.getStatus(), e);
            throw new IllegalArgumentException(" Invalid car status! Allowed values: Available, Maintenance, Assigned");
        }
    }

    public void deleteCar(String id) {
        carRepository.deleteById(id);
        logger.info("🗑️ Car Deleted: ID {}", id);
    }
}
