package org.example;

import org.example.exceptions.InvalidAttendantException;
import org.example.exceptions.ParkingLotFullException;
import org.example.interfaces.ParkingLotSubscriber;
import org.example.interfaces.ParkingStrategy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Attendant implements ParkingLotSubscriber {
    private final String name;
    private final ParkingStrategy parkingStrategy;
    private final List<ParkingLot> parkingLots;

    public Attendant(String name, ParkingStrategy parkingStrategy) {
        if (name == null) {
            throw new InvalidAttendantException();
        }

        this.name = name;
        this.parkingLots = new LinkedList<>();
        this.parkingStrategy = parkingStrategy;
    }

    public void add(ParkingLot parkingLot) {
        this.parkingLots.add(parkingLot);
    }

    public List<ParkingLot> parkingLots() {
        return new ArrayList<>(this.parkingLots);
    }

    public Ticket park(Car car) {
        for (int ii = 0; ii < parkingLots.size(); ii++) {
            try {
                Ticket ticket = parkingStrategy.park(parkingLots.get(ii), car, ii);
                if (ticket != null) {
                    return ticket;
                }
            } catch (ParkingLotFullException e) {
                continue;
            }
        }

        throw new ParkingLotFullException();
    }

    public Car unpark(Ticket ticket, String registrationNumber) {
        for (ParkingLot parkingLot : this.parkingLots) {
            Car car = parkingLot.unpark(ticket, registrationNumber);
            if (car != null) {
                return car;
            }
        }

        return null;
    }

    public boolean isCarParked(Car car) {
        for (ParkingLot parkingLot : this.parkingLots) {
            if (parkingLot.isCarParked(car)) {
                return true;
            }
        }

        return false;
    }

    public int countCarsByColor(Color color) {
        int count = 0;
        for (ParkingLot parkingLot : this.parkingLots) {
            count += parkingLot.countCarsByColor(color);
        }

        return count;
    }
}
