package org.example;

import org.example.exceptions.InvalidAttendantException;
import org.example.exceptions.ParkingLotFullException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Attendant {
    private final String name;
    private final List<ParkingLot> parkingLots;

    public Attendant(String name) {
        if (name == null) {
            throw new InvalidAttendantException();
        }

        this.name = name;
        this.parkingLots = new LinkedList<>();
    }

    public void add(ParkingLot parkingLot) {
        this.parkingLots.add(parkingLot);
    }

    public List<ParkingLot> parkingLots() {
        return new ArrayList<>(this.parkingLots);
    }

    public Ticket park(Car car) {
        for (int ii = 0; ii < this.parkingLots.size(); ii++) {
            ParkingLot parkingLot = this.parkingLots.get(ii);
            if (!parkingLot.isAtFullCapacity() || parkingLot.getEmptySlot() != null) {
                return parkingLot.park(car, ii);
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
