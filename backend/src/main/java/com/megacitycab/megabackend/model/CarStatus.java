package com.megacitycab.megabackend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CarStatus {
    AVAILABLE("AVAILABLE"),
    MAINTENANCE("MAINTENANCE"),
    ASSIGNED("ASSIGNED");

    private final String value;

    CarStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static CarStatus fromString(String value) {
        for (CarStatus status : CarStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException(" Invalid car status: " + value);
    }
}
