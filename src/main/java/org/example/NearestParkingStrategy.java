package org.example;

import org.example.interfaces.ParkingStrategy;

public class NearestParkingStrategy implements ParkingStrategy {
    @Override
    public Ticket park(ParkingLot parkingLot, Car car, int level) {
        return parkingLot.parkFromNearest(car, level);
    }
}
