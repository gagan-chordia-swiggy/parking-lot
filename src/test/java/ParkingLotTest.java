import org.example.ParkingLot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}
