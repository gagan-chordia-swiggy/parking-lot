package org.example;

import org.example.exceptions.ParkingLotFullException;

public class MultiLevelParkingLot {
    private final ParkingLot[] parkingLots;
    private final int levels;

    public MultiLevelParkingLot(int levels, int capacityOfParkingLots) {
        if (levels < 1) {
            throw new IllegalArgumentException();
        }

        this.levels = levels;
        this.parkingLots = new ParkingLot[levels];

        for (int ii = 0; ii < levels; ii++) {
            this.parkingLots[ii] = new ParkingLot(capacityOfParkingLots);
        }
    }

    public Ticket park(Car car) {
        Ticket ticket = null;

        for (int ii = 0; ii < this.levels; ii++) {
            if (ii == this.levels - 1 && this.parkingLots[ii].isAtFullCapacity() && this.parkingLots[ii].getEmptySlot() == null) {
                throw new ParkingLotFullException();
            }

            if (!this.parkingLots[ii].isAtFullCapacity() || this.parkingLots[ii].getEmptySlot() != null) {
                ticket = this.parkingLots[ii].park(car, ii);
                break;
            }
        }

        return ticket;
    }

    public Car unpark(Ticket ticket, String registrationNumber) {
        return this.parkingLots[ticket.level()].unpark(ticket, registrationNumber);
    }

    public boolean isCarParked(Car car) {
        for (int ii = 0; ii < this.levels; ii++) {
            if (this.parkingLots[ii].isCarParked(car)) {
                return true;
            }
        }

        return false;
    }

    public int countCarsByColor(Color color) {
        int count = 0;
        for (int ii = 0; ii < this.levels; ii++) {
            count += this.parkingLots[ii].countCarsByColor(color);
        }

        return count;
    }
}
