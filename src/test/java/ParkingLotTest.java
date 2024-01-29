import org.example.Car;
import org.example.Color;
import org.example.ParkingLot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParkingLotTest {
    @Test
    void testParkingLotIsCreated() {
        // Arrange, Act
        ParkingLot parkingLot = new ParkingLot(12);

        // Assert
        assertNotNull(parkingLot);
    }

    @Test
    void testParkingLotWithZeroSlots() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(0));
    }

    @Test
    void testParkingLotWithNegative5Slots() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(-5));
    }

    @Test
    void testParkingLotWithNegative1Slots() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(-1));
    }

    @Test
    void testACarIsParkedInTheParkingSlot() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(10);
        Car car = new Car("AP00AD1234", Color.BLUE);

        // Act
        parkingLot.parkCar(car);
        boolean actual = parkingLot.isCarParked(car);

        // Assert
        assertTrue(actual);
    }

    @Test
    void testACarIsNotPresentInTheParkingSlot() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car("AP00AD1234", Color.BLUE);

        // Act
        boolean actual = parkingLot.isCarParked(car);

        // Assert
        assertFalse(actual);
    }
}
