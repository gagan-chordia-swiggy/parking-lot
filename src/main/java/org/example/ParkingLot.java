package org.example;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    private final Map<Integer, Car> parkingSlots;
    private final int capacity;

    public ParkingLot(int capacity) {
        if(capacity < 1) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        this.parkingSlots = new HashMap<>();
    }
}
