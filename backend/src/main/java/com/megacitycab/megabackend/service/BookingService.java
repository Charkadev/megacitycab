package com.megacitycab.megabackend.service;

import com.megacitycab.megabackend.model.Booking;
import com.megacitycab.megabackend.model.BookingStatus;
import com.megacitycab.megabackend.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;

    // ✅ Create a Booking
    public Booking createBooking(Booking booking) {
        booking.setStatus(BookingStatus.PENDING);
        booking.setTimestamp(LocalDateTime.now());
        return bookingRepository.save(booking);
    }

    // ✅ Get Booking by ID
    public Optional<Booking> getBookingById(String id) {
        return bookingRepository.findById(id);
    }

    // ✅ Get Bookings by User ID
    public List<Booking> getUserBookings(String userId) {
        return bookingRepository.findByUserId(userId);
    }

    // ✅ Get All Bookings (For Admin)
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // ✅ Cancel a Booking
    public Booking cancelBooking(String id) {
        Optional<Booking> bookingOpt = bookingRepository.findById(id);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            booking.setStatus(BookingStatus.CANCELLED);
            return bookingRepository.save(booking);
        }
        throw new RuntimeException("Booking not found");
    }
}
