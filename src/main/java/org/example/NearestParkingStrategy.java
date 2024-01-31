package org.example;

import org.example.exceptions.ParkingLotFullException;
import org.example.interfaces.ParkingStrategy;

import java.util.List;

public class NearestParkingStrategy implements ParkingStrategy {
    @Override
    public Ticket park(List<ParkingLot> parkingLots, Car car) {
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
}
