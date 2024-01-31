package org.example;

import org.example.interfaces.ParkingStrategy;

public class FarthestParkingStrategy implements ParkingStrategy {
    @Override
    public Ticket park(ParkingLot parkingLot, Car car, int level) {
        return parkingLot.parkFromFarthest(car, level);
    }
}
