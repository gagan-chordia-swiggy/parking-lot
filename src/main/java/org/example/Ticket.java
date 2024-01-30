package org.example;

public record Ticket(int level, int slot, String registrationNumber) {
    public Ticket {
        if (level < 0) {
            throw new InvalidTicketException();
        }
    }
}
