package com.megacitycab.megabackend.repository;

import com.megacitycab.megabackend.model.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CarRepository extends MongoRepository<Car, String> {
    List<Car> findByStatus(String status); //  Store & filter status as String
}
