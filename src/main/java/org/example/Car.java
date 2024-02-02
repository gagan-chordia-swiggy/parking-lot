package org.example;

import org.example.enums.Color;
import org.example.exceptions.InvalidRegistrationNumberException;

public record Car(String registrationNumber, Color color) {
    public Car {
        String validRegistrationNumberPattern = "^[A-Z]{2}\\d{2}[A-Z]{2}\\d{4}";

        if (!registrationNumber.matches(validRegistrationNumberPattern)) {
            throw new InvalidRegistrationNumberException();
        }

    }
}
