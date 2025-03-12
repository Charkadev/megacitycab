package com.megacitycab.megabackend.service;

import com.megacitycab.megabackend.model.Booking;
import com.megacitycab.megabackend.model.BookingStatus;
import com.megacitycab.megabackend.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminReportService {
    private final BookingRepository bookingRepository;

    // ✅ Generate Total Earnings Report (Summing from all COMPLETED bookings)
    public double getTotalEarnings() {
        List<Booking> completedBookings = bookingRepository.findAll();

        return completedBookings.stream()
                .filter(booking -> booking.getStatus() == BookingStatus.COMPLETED) // ✅ Only COMPLETED bookings
                .mapToDouble(Booking::getFare) // ✅ Sum fares of completed bookings
                .sum();
    }

    // ✅ Generate Booking Summary Report
    public Map<String, Long> getBookingSummary() {
        Map<String, Long> summary = new HashMap<>();
        summary.put("Total Bookings", bookingRepository.count());
        summary.put("Completed Bookings", bookingRepository.findAll().stream()
                .filter(booking -> booking.getStatus() == BookingStatus.COMPLETED).count());
        summary.put("Pending Bookings", bookingRepository.findAll().stream()
                .filter(booking -> booking.getStatus() == BookingStatus.PENDING).count());
        summary.put("Cancelled Bookings", bookingRepository.findAll().stream()
                .filter(booking -> booking.getStatus() == BookingStatus.CANCELLED).count());
        return summary;
    }

    // ✅ Get User Booking Report
    public List<Booking> getUserBookings(String userId) {
        return bookingRepository.findByUserId(userId);
    }
}
