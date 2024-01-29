package org.example;

public class MultiLevelParkingLot {
    private final ParkingLot[] parkingLots;
    private final int totalLots;

    public MultiLevelParkingLot(int totalLots, int capacityOfParkingLots) {
        this.totalLots = totalLots;
        this.parkingLots = new ParkingLot[totalLots];

        for (int ii = 0; ii < totalLots; ii++) {
            this.parkingLots[ii] = new ParkingLot(capacityOfParkingLots);
        }
    }
}
