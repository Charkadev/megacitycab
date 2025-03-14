package com.megacitycab.megabackend.service;

import com.megacitycab.megabackend.model.Billing;
import com.megacitycab.megabackend.model.Booking;
import com.megacitycab.megabackend.repository.BillingRepository;
import com.megacitycab.megabackend.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillingService {
    private final BillingRepository billingRepository;
    private final BookingRepository bookingRepository;

    private static final double TAX_RATE = 0.10; // 10% tax rate

    //  Generate a bill for a completed booking
    public Billing generateBill(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getStatus().equals("COMPLETED")) {
            throw new RuntimeException("Bill can only be generated for completed bookings.");
        }

        // Check if a bill already exists
        Optional<Billing> existingBill = billingRepository.findByBookingId(bookingId);
        if (existingBill.isPresent()) {
            return existingBill.get();
        }

        // Calculate tax and total amount
        double tax = booking.getFare() * TAX_RATE;
        double totalAmount = booking.getFare() + tax;

        Billing bill = Billing.builder()
                .bookingId(booking.getId())
                .userId(booking.getUserId())
                .fare(booking.getFare())
                .tax(tax)
                .totalAmount(totalAmount)
                .timestamp(LocalDateTime.now())
                .build();

        return billingRepository.save(bill);
    }

    //  Get All Bills (Admin Only)
    public List<Billing> getAllBills() {
        return billingRepository.findAll();
    }

    //  Get Billing History for a Specific User
    public List<Billing> getUserBills(String userId) {
        return billingRepository.findByUserId(userId);
    }
}
