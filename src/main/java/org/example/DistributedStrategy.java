package org.example;

import org.example.interfaces.ParkingStrategy;

public class DistributedStrategy implements ParkingStrategy {
    @Override
    public Ticket park(ParkingLot parkingLot, Car car, int level) {
        return parkingLot.parkDistributively(car, level);
    }
}
