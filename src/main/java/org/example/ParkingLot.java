package org.example;

import org.example.exceptions.CarNotFoundException;
import org.example.exceptions.ParkingLotFullException;
import org.example.exceptions.SameCarParkedException;

import java.util.Collections;
import java.util.TreeMap;
import java.util.Map;

public class ParkingLot {
    private final Map<Ticket, Car> parkingSlots;
    private int frontSlotsFilled;
    private int backSlotsFilled;

    public ParkingLot(int capacity) {
        if(capacity < 1) {
            throw new IllegalArgumentException();
        }

        this.parkingSlots = new TreeMap<>();
        this.frontSlotsFilled = 0;
        this.backSlotsFilled = capacity;
    }

    public Ticket park(Car car, int level, boolean fromNearest) {
        checkForSameCarParked(car);

        if (fromNearest) {
            return parkFromNearest(car, level);
        }

        return parkFromFarthest(car, level);
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
            if (!isParkingAvailable()) {
                System.out.println("There is vacancy");
            }

            parkingSlots.put(ticket, null);
            return car;
        }

        throw new CarNotFoundException();
    }

    public boolean isAtFullCapacity() {
        return this.frontSlotsFilled - this.backSlotsFilled == 0;
    }

    public Ticket getEmptySlotFromFront() {
        for (Map.Entry<Ticket, Car> entry : parkingSlots.entrySet()) {
            if (entry.getValue() == null) {
                return entry.getKey();
            }
        }

        return null;
    }

    public Ticket getEmptySlotFromLast() {
        Map<Ticket, Car> reversedParkingSlots = new TreeMap<>(Collections.reverseOrder());
        reversedParkingSlots.putAll(this.parkingSlots);

        for (Map.Entry<Ticket, Car> entry : reversedParkingSlots.entrySet()) {
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

    private boolean isParkingAvailable() {
        return !isAtFullCapacity() || getEmptySlotFromFront() != null;
    }

    private Ticket parkFromNearest(Car car, int level) {
        Ticket ticket = getEmptySlotFromFront();

        if (ticket != null) {
            parkingSlots.put(ticket, car);
            return ticket;
        }

        if (isAtFullCapacity()) {
            System.out.println("Parking lot is full");
            throw new ParkingLotFullException();
        }

        Ticket parkingTicket = new Ticket(level, this.frontSlotsFilled++);

        parkingSlots.put(parkingTicket, car);
        return parkingTicket;
    }

    private Ticket parkFromFarthest(Car car, int level) {
        Ticket ticket = getEmptySlotFromLast();

        if (ticket != null) {
            parkingSlots.put(ticket, car);
            return ticket;
        }

        if (isAtFullCapacity()) {
            System.out.println("Parking lot is full");
            throw new ParkingLotFullException();
        }

        Ticket parkingTicket = new Ticket(level, this.backSlotsFilled--);

        parkingSlots.put(parkingTicket, car);
        return parkingTicket;
    }
}
