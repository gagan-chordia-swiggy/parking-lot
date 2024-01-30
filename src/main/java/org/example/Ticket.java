package org.example;

import org.example.exceptions.InvalidTicketException;

public record Ticket(int level, int slot) implements Comparable<Ticket> {
    public Ticket {
        if (level < 0 || slot < 0) {
            throw new InvalidTicketException();
        }
    }

    @Override
    public int compareTo(Ticket t) {
        if (this.level > t.level()) {
            return 1;
        }

        if (this.level == t.level() && this.slot > t.slot()) {
            return 1;
        }

        if (this.level == t.level() && this.slot == t.slot()) {
            return 0;
        }

        return -1;
    }
}
