package org.example;

import org.example.exceptions.InvalidTicketException;

public record Ticket(int level, int slot) {
    public Ticket {
        if (level < 0 || slot < 0) {
            throw new InvalidTicketException();
        }
    }
}
