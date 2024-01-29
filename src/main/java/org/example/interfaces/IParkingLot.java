package org.example.interfaces;

import org.example.Car;
import org.example.Color;

public interface IParkingLot {
    String park(Car car);
    Car unpark(String slotNumber, String registrationNumber);
    boolean isCarParked(Car car);
    int countCarsByColor(Color color);
}
