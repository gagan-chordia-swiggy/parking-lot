package org.example.interfaces;

import org.example.Car;
import org.example.ParkingLot;
import org.example.Ticket;

public interface ParkingStrategy {
    Ticket park(ParkingLot parkingLot, Car car, int level);
}
