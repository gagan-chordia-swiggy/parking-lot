package org.example;

import java.util.LinkedList;
import java.util.List;

public class Attendant {
    private final String name;
    private final List<Assignment> assignments;

    public Attendant(String name) {
        this.name = name;
        this.assignments = new LinkedList<>();
    }
}
