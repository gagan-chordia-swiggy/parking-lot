import org.example.Car;
import org.example.enums.Color;
import org.example.exceptions.InvalidRegistrationNumberException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CarTest {
    @Test
    void testCarIsCreated() {
        // Arrange & Act
        Car car = new Car("TN12AB1234", Color.BLACK);

        // Assert
        assertNotNull(car);
    }

    @Test
    void testInvalidRegistrationCarThrowsException() {
        // Assert
        assertThrows(InvalidRegistrationNumberException.class, () -> new Car("12CD", Color.RED));
    }
}
