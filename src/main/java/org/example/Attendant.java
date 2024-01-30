package org.example;

import org.example.exceptions.InvalidAttendantException;

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
        assignments.add(assignment);
    }
}
