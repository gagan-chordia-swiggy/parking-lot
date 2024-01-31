package org.example;

import org.example.exceptions.InvalidAttendantException;
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
        return parkingStrategy.park(parkingLots, car);
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
    public void notify(ParkingLotEvent event, Object publisher) {
        if (!parkingLots.contains((ParkingLot) publisher)) {
            return;
        }

        ParkingLotSubscriber.super.notify(event, publisher);
    }
}
