package com.megacitycab.megabackend.repository;

import com.megacitycab.megabackend.model.Billing;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BillingRepository extends MongoRepository<Billing, String> {
    Optional<Billing> findByBookingId(String bookingId);
    List<Billing> findByUserId(String userId);
}
