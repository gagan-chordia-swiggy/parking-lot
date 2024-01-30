package org.example;

import org.example.exceptions.InvalidAttendantException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Attendant {
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

        assignments.add(assignment);
    }

    public String name() {
        return new String(name);
    }

    public List<Assignment> assignments() {
        return new ArrayList<>(assignments);
    }
}
