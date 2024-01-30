package org.example;

import org.example.exceptions.CarNotFoundException;
import org.example.exceptions.ParkingLotFullException;
import org.example.exceptions.SameCarParkedException;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    private final Map<Ticket, Car> parkingSlots;
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

    public Ticket park(Car car, int level) {
        checkForSameCarParked(car);

        Ticket ticket = getEmptySlot();

        if (ticket != null) {
            parkingSlots.put(ticket, car);
            return ticket;
        }

        if (isAtFullCapacity()) {
            throw new ParkingLotFullException();
        }

        Ticket parkingTicket = new Ticket(level, this.nextSlotAvailable++);

        parkingSlots.put(parkingTicket, car);
        return parkingTicket;
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

    public Car unpark(Ticket ticket, String registrationNumber) {
        Car car = parkingSlots.get(ticket);
        if (car != null && car.registrationNumber().equals(registrationNumber)) {
            parkingSlots.put(ticket, null);
            return car;
        }

        throw new CarNotFoundException();
    }

    public boolean isAtFullCapacity() {
        return this.nextSlotAvailable > this.capacity;
    }

    public Ticket getEmptySlot() {
        for (Map.Entry<Ticket, Car> entry : parkingSlots.entrySet()) {
            if (entry.getValue() == null) {
                return entry.getKey();
            }
        }

        return null;
    }

    private void checkForSameCarParked(Car car) {
        if (isCarParked(car)) {
            throw new SameCarParkedException();
        }
    }
}
