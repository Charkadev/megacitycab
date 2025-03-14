package com.megacitycab.megabackend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "drivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {
    @Id
    private String id;
    private String name;
    private String phone;
    private Boolean availability;
    private String assignedCarId;

    public Boolean isAvailable() {
        return availability;
    }

    public void assignCar(String carId) {
        this.assignedCarId = carId;
        this.availability = false; //  Driver is now unavailable
    }

    public void unassignCar() {
        this.assignedCarId = null;
        this.availability = true; //  Driver becomes available
    }
}
