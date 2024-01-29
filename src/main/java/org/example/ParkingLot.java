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

    public int park(Car car) {
        checkForSameCarParked(car);

        for (Map.Entry<Integer, Car> entry : parkingSlots.entrySet()) {
            if (entry.getValue() == null) {
                parkingSlots.put(entry.getKey(), car);
                return entry.getKey();
            }
        }

        isAtFullCapacity();

        parkingSlots.put(this.nextSlotAvailable, car);
        return this.nextSlotAvailable++;
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

    public Car unpark(int slotNumber, String registrationNumber) {
        Car car = parkingSlots.get(slotNumber);
        if (car != null && car.registrationNumber().equals(registrationNumber)) {
            parkingSlots.put(slotNumber, null);
            return car;
        }

        throw new IllegalArgumentException("Car not found. Thus, cannot be unparked");
    }

    private void checkForSameCarParked(Car car) {
        if (isCarParked(car)) {
            throw new IllegalArgumentException("Same car cannot be parked again");
        }
    }

    private void isAtFullCapacity() {
        if (this.nextSlotAvailable > this.capacity) {
            throw new RuntimeException("Full capacity");
        }
    }
}
