package org.example.interfaces;

import org.example.ParkingLot;

public interface ParkingLotObserver {
    void notifyFull(ParkingLot parkingLot);
    void notifyVacant(ParkingLot parkingLot);
}
