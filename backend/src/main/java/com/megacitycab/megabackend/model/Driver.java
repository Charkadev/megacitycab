package com.megacitycab.megabackend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "drivers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
    @Id
    private String id;
    private String name;
    private String licenseNumber;
    private String phone;
    private boolean available; // ✅ Used to assign available drivers to bookings
}
