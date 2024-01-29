import org.example.Car;
import org.example.Color;
import org.example.MultiLevelParkingLot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MultiLevelParkingLotTest {
    @Test
    void testMultiLevelParkingLotsAreCreated() {
        // Arrange, Act
        MultiLevelParkingLot multiLevelParkingLot = new MultiLevelParkingLot(2, 1);

        // Assert
        assertNotNull(multiLevelParkingLot);
    }

    @Test
    void testMultiLevelParkingLotWithCapacityZeroThrowsError() {
        assertThrows(IllegalArgumentException.class, () -> new MultiLevelParkingLot(2, 0));
    }

    @Test
    void testMultiLevelParkingLotWithNegativeCapacityThrowsError() {
        assertThrows(IllegalArgumentException.class, () -> new MultiLevelParkingLot(2, -1));
    }

    @Test
    void testMultiLevelParkingLotWithZeroLevelsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new MultiLevelParkingLot(0, 12));
    }

    @Test
    void testMultiLevelParkingLotWithNegative1LevelThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new MultiLevelParkingLot(-1, 12));
    }

    @Test
    void testMultiLevelParkingLotWithNegative15LevelThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new MultiLevelParkingLot(-15, 12));
    }

    @Test
    void testParkingAtInitialLevelShouldReturnSlot() {
        // Arrange
        MultiLevelParkingLot multiLevelParkingLot = new MultiLevelParkingLot(2, 2);
        Car car = new Car("AB12FR1234", Color.WHITE);

        // Act
        String actual = multiLevelParkingLot.park(car);

        // Assert
        assertEquals("0 - 1", actual);
    }
}
