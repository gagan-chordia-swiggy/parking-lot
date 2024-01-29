package org.example;

import org.example.interfaces.IParkingLot;

public class MultiLevelParkingLot implements IParkingLot {
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

    @Override
    public String park(Car car) {
        StringBuilder slot = new StringBuilder();

        for (int ii = 0; ii < levels; ii++) {
            if (!this.parkingLots[ii].isAtFullCapacity() || this.parkingLots[ii].getEmptySlot().containsKey(true)) {
                slot.append(ii);
                slot.append(" - ");
                String levelSlot = this.parkingLots[ii].park(car);
                slot.append(levelSlot);
                break;
            }
        }

        return slot.toString();
    }

    @Override
    public Car unpark(String slotNumber, String registrationNumber) {
        return null;
    }

    @Override
    public boolean isCarParked(Car car) {
        return false;
    }

    @Override
    public int countCarsByColor(Color color) {
        return 0;
    }
}
