package com.megacitycab.megabackend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "bookings")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    private String id;
    private String userId;
    private String driverId;
    private String pickupLocation;
    private String dropoffLocation;
    private double fare;
    private BookingStatus status;
    private LocalDateTime timestamp;
}
