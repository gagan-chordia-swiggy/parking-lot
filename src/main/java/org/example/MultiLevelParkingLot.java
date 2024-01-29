package org.example;

public class MultiLevelParkingLot {
    private final ParkingLot[] parkingLots;
    private final int levels;

    public MultiLevelParkingLot(int levels, int capacityOfParkingLots) {
        if (levels == 0) {
            throw new IllegalArgumentException();
        }

        this.levels = levels;
        this.parkingLots = new ParkingLot[levels];

        for (int ii = 0; ii < levels; ii++) {
            this.parkingLots[ii] = new ParkingLot(capacityOfParkingLots);
        }
    }
}
