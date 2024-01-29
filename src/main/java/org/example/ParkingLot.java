package org.example;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    private final Map<Integer, Car> parkingSlots;
    private final int capacity;
    private int nextSlotAvailable;

    public ParkingLot(int capacity) {
        if(capacity < 1) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        this.parkingSlots = new HashMap<>();
        this.nextSlotAvailable = 1;
    }

    public void parkCar(Car car) {
        if (isCarParked(car)) {
            throw new IllegalArgumentException("Same car cannot be parked again");
        }

        if (nextSlotAvailable > capacity) {
            throw new RuntimeException("Full capacity");
        }

        parkingSlots.put(nextSlotAvailable, car);
        ++nextSlotAvailable;
    }

    public boolean isCarParked(Car carToBeChecked) {
        for (Car car : parkingSlots.values()) {
            if (car != null && car.equals(carToBeChecked)) {
                return true;
            }
        }

        return false;
    }

    public int countCarsByColor(Color color) {
        int count = 0;
        for (Car car : parkingSlots.values()) {
            if (car != null && car.color().equals(color)) {
                ++count;
            }
        }

        return count;
    }
}
