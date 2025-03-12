package com.megacitycab.megabackend.repository;

import com.megacitycab.megabackend.model.Billing;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface BillingRepository extends MongoRepository<Billing, String> {
    List<Billing> findByUserId(String userId); // Retrieve bills for a specific user
}
