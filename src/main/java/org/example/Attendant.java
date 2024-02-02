package org.example;

import org.example.enums.Color;
import org.example.enums.ParkingLotEvent;
import org.example.exceptions.InvalidAttendantException;
import org.example.exceptions.ParkingLotFullException;
import org.example.interfaces.ParkingLotPublisher;
import org.example.interfaces.ParkingLotSubscriber;
import org.example.enums.ParkingStrategy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Attendant implements ParkingLotSubscriber {
    private final String name;
    private ParkingStrategy parkingStrategy;
    private final List<ParkingLot> parkingLots;
    private int index = 0;

    public Attendant(String name, ParkingStrategy parkingStrategy) {
        if (name == null) {
            throw new InvalidAttendantException();
        }

        this.name = name;
        this.parkingLots = new LinkedList<>();
        this.parkingStrategy = parkingStrategy;
    }

    public void setParkingStrategy(ParkingStrategy parkingStrategy) {
        this.parkingStrategy = parkingStrategy;
    }

    public void add(ParkingLot parkingLot) {
        this.parkingLots.add(parkingLot);
    }

    public List<ParkingLot> parkingLots() {
        return new ArrayList<>(this.parkingLots);
    }

    public Ticket park(Car car) {
        if (this.parkingStrategy == ParkingStrategy.NEAREST) {
            return parkNearBy(car);
        }

        if (this.parkingStrategy == ParkingStrategy.FARTHEST) {
            return parkFarAway(car);
        }

        return parkDistributively(car);
    }

    public Car unpark(Ticket ticket, String registrationNumber) {
        return parkingLots.get(ticket.level()).unpark(ticket, registrationNumber);
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

    @Override
    public void notify(ParkingLotEvent event, ParkingLotPublisher publisher) {
        if (!parkingLots.contains((ParkingLot) publisher)) {
            return;
        }

        ParkingLotSubscriber.super.notify(event, publisher);
    }

    public Ticket parkNearBy(Car car) {
        for (int ii = 0; ii < parkingLots.size(); ii++) {
            ParkingLot lot = parkingLots.get(ii);
            try {
                if (!lot.isAtFullCapacity() || lot.getEmptySlotFromFront() != null) {
                    return lot.parkFromNearest(car, ii);
                }
            } catch (ParkingLotFullException e) {
                continue;
            }
        }

        throw new ParkingLotFullException();
    }

    public Ticket parkFarAway(Car car) {
        for (int ii = parkingLots.size() - 1; ii >= 0; ii--) {
            ParkingLot lot = parkingLots.get(ii);
            try {
                if (!lot.isAtFullCapacity() || lot.getEmptySlotFromFront() != null) {
                    return lot.parkFromFarthest(car, ii);
                }
            } catch (ParkingLotFullException e) {
                continue;
            }
        }

        throw new ParkingLotFullException();
    }

    public Ticket parkDistributively(Car car) {
        for (int ii = 0; ii < parkingLots.size(); ii++) {
            ParkingLot currentLot = parkingLots.get(index);

            try {
                Ticket ticket = currentLot.parkFromNearest(car, index);
                index = (index + 1) % parkingLots.size();
                return ticket;
            } catch (ParkingLotFullException e) {
                index = (index + 1) % parkingLots.size();
            }
        }

        throw new ParkingLotFullException();
    }
}
