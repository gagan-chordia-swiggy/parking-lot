package org.example;

import org.example.exceptions.InvalidRegistrationNumberException;
import org.example.exceptions.InvalidTicketException;

public record Ticket(int level, int slot, String registrationNumber) {
    public Ticket {
        if (level < 0 || slot < 0) {
            throw new InvalidTicketException();
        }

        String validRegistrationNumberPattern = "^[A-Z]{2}\\d{2}[A-Z]{2}\\d{4}";

        if (!registrationNumber.matches(validRegistrationNumberPattern)) {
            throw new InvalidRegistrationNumberException();
        }
    }
}
