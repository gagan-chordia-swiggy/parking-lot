package org.example;

import org.example.exceptions.CarNotFoundException;
import org.example.exceptions.ParkingLotFullException;
import org.example.exceptions.SameCarParkedException;
import org.example.interfaces.ParkingLotSubscriber;

import java.util.*;

public class ParkingLot {
    private final Map<Ticket, Car> parkingSlots;
    private int nextAvailableSlot;
    private int availableSlotFromEnd;
    private final int capacity;

    public ParkingLot(int capacity) {
        if(capacity < 1) {
            throw new IllegalArgumentException();
        }

        this.parkingSlots = new TreeMap<>();
        this.nextAvailableSlot = 1;
        this.capacity = capacity;
        this.availableSlotFromEnd = capacity;
    }

    public Ticket park(Car car, int level, boolean fromNearest) {
        checkForSameCarParked(car);

        return fromNearest ? parkFromNearest(car, level) : parkFromFarthest(car, level);
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
                notifyVacant();
            }

            parkingSlots.put(ticket, null);
            return car;
        }

        throw new CarNotFoundException();
    }

    public boolean isAtFullCapacity() {
        return Math.abs(this.nextAvailableSlot - this.availableSlotFromEnd) == this.capacity;
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

    public Ticket parkFromNearest(Car car, int level) {
        checkForSameCarParked(car);
        Ticket ticket = getEmptySlotFromFront();

        if (ticket != null) {
            parkingSlots.put(ticket, car);
            return ticket;
        }

        if (isAtFullCapacity()) {
            throw new ParkingLotFullException();
        }

        Ticket parkingTicket = new Ticket(level, this.nextAvailableSlot++);

        parkingSlots.put(parkingTicket, car);
        if (!isParkingAvailable()) {
            notifyFull();
        }
        return parkingTicket;
    }

    public Ticket parkFromFarthest(Car car, int level) {
        checkForSameCarParked(car);
        Ticket ticket = getEmptySlotFromLast();

        if (ticket != null) {
            parkingSlots.put(ticket, car);
            return ticket;
        }

        if (isAtFullCapacity()) {
            throw new ParkingLotFullException();
        }

        Ticket parkingTicket = new Ticket(level, this.availableSlotFromEnd--);

        parkingSlots.put(parkingTicket, car);
        if (!isParkingAvailable()) {
            notifyFull();
        }
        return parkingTicket;
    }

    public Ticket parkDistributively(Car car, int level) {
        checkForSameCarParked(car);

        boolean distribution = new Random().nextBoolean();

        return distribution ? parkFromNearest(car, level) : parkFromFarthest(car, level);
    }

    private void notifyFull() {
        NotificationBus.instance().publish(this, ParkingLotEvent.FULL);
    }

    private void notifyVacant() {
        NotificationBus.instance().publish(this, ParkingLotEvent.EMPTY);
    }
}
