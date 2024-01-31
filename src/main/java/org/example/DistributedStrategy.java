package org.example;

import org.example.exceptions.ParkingLotFullException;
import org.example.interfaces.ParkingStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DistributedStrategy implements ParkingStrategy {
    @Override
    public Ticket park(List<ParkingLot> parkingLots, Car car) {
        List<ParkingLot> temporaryLots = new ArrayList<>(parkingLots);
        Collections.shuffle(temporaryLots);
        int iterator = 0;

        while (!temporaryLots.isEmpty()) {
            ParkingLot lot = temporaryLots.get(iterator);
            int index = parkingLots.indexOf(lot);

            try {
                if (!lot.isAtFullCapacity() || lot.getEmptySlotFromFront() != null) {
                    return lot.parkDistributively(car, index);
                }
            } catch (ParkingLotFullException e) {
                continue;
            }
        }

        throw new ParkingLotFullException();
    }
}
