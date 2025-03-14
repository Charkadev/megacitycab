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

    //  Get Total Earnings Report (Summing from all COMPLETED bookings)
    public double getTotalEarnings() {
        List<Booking> completedBookings = bookingRepository.findByStatus(BookingStatus.COMPLETED);
        return completedBookings.stream()
                .mapToDouble(Booking::getFare) //  Sum fares of completed bookings
                .sum();
    }

    //  Get Booking Summary Report
    public Map<String, Long> getBookingSummary() {
        Map<String, Long> summary = new HashMap<>();
        summary.put("Total Bookings", bookingRepository.count());
        summary.put("Completed Bookings", bookingRepository.countByStatus(BookingStatus.COMPLETED));
        summary.put("Pending Bookings", bookingRepository.countByStatus(BookingStatus.PENDING));
        summary.put("Cancelled Bookings", bookingRepository.countByStatus(BookingStatus.CANCELLED));
        return summary;
    }

    //  Fetch All Bookings (Admin Only)
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    //  Fetch User Booking Report
    public List<Booking> getUserBookings(String userId) {
        return bookingRepository.findByUserId(userId);
    }
}
