package com.megacitycab.megabackend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "driver_earnings")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverEarnings {
    @Id
    private String id;
    private String driverId;
    private double totalEarnings;
}
