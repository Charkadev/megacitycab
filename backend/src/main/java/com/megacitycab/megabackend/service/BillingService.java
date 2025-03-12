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

    // Generate a bill when a booking is completed
    public Billing generateBill(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Check if billing already exists for this booking
        Optional<Billing> existingBill = billingRepository.findAll().stream()
                .filter(bill -> bill.getBookingId().equals(bookingId))
                .findFirst();

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

    // Get all bills (Admin only)
    public List<Billing> getAllBills() {
        List<Billing> bills = billingRepository.findAll();
        if (bills.isEmpty()) {
            throw new RuntimeException("No billing records found.");
        }
        return bills;
    }

    // Get bills for a specific user
    public List<Billing> getUserBills(String userId) {
        List<Billing> userBills = billingRepository.findByUserId(userId);
        if (userBills.isEmpty()) {
            throw new RuntimeException("No bills found for the given user.");
        }
        return userBills;
    }
}
