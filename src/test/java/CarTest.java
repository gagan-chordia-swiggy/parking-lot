import org.example.Car;
import org.example.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CarTest {
    @Test
    void testCarIsCreated() {
        // Arrange & Act
        Car car = new Car("TN12AB1234", Color.BLACK);

        // Assert
        assertNotNull(car);
    }
}
