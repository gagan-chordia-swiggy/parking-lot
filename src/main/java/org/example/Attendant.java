package org.example;

import org.example.exceptions.InvalidAttendantException;
import org.example.exceptions.ParkingLotFullException;
import org.example.interfaces.Parking;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Attendant implements Parking {
    private final String name;
    private final List<Assignment> assignments;

    public Attendant(String name) {
        if (name == null) {
            throw new InvalidAttendantException();
        }

        this.name = name;
        this.assignments = new LinkedList<>();
    }

    public void add(Assignment assignment) {
        if (assignment.attendant() != this) {
            throw new InvalidAttendantException();
        }

        this.assignments.add(assignment);
    }

    public String name() {
        return new String(this.name);
    }

    public List<Assignment> assignments() {
        return new ArrayList<>(this.assignments);
    }

    @Override
    public Ticket park(Car car, int level) {
        for (int ii = 0; ii < this.assignments.size(); ii++) {
            ParkingLot parkingLot = this.assignments.get(ii).parkingLot();
            if (!parkingLot.isAtFullCapacity() || parkingLot.getEmptySlot() != null) {
                return parkingLot.park(car, ii);
            }
        }

        throw new ParkingLotFullException();
    }

    @Override
    public Car unpark(Ticket ticket, String registrationNumber) {
        return null;
    }

    @Override
    public boolean isCarParked(Car car) {
        for (Assignment assignment : this.assignments) {
            if (assignment.parkingLot().isCarParked(car)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int countCarsByColor(Color color) {
        int count = 0;
        for (Assignment assignment : this.assignments) {
            count += assignment.parkingLot().countCarsByColor(color);
        }

        return count;
    }
}
