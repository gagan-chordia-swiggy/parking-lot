package org.example;

import org.example.exceptions.ParkingLotFullException;
import org.example.interfaces.ParkingStrategy;

import java.util.List;

public class DistributedStrategy implements ParkingStrategy {
    private int index = 0;

    @Override
    public Ticket park(List<ParkingLot> parkingLots, Car car) {
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
