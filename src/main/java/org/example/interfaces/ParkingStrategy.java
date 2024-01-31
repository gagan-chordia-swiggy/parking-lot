package org.example.interfaces;

import org.example.Car;
import org.example.ParkingLot;
import org.example.Ticket;

import java.util.List;

public interface ParkingStrategy {
    Ticket park(List<ParkingLot> parkingLots, Car car);
}
