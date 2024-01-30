package org.example.interfaces;

import org.example.Car;
import org.example.Color;
import org.example.Ticket;

public interface Parking {
    Ticket park(Car car, int level);
    Car unpark(Ticket ticket, String registrationNumber);
    boolean isCarParked(Car car);
    int countCarsByColor(Color color);
}
