package org.example;

import org.example.exceptions.ParkingLotFullException;
import org.example.interfaces.ParkingStrategy;

import java.util.List;

public class FarthestParkingStrategy implements ParkingStrategy {
    @Override
    public Ticket park(List<ParkingLot> parkingLots, Car car) {
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
}
