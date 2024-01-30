import org.example.Car;
import org.example.Color;
import org.example.MultiLevelParkingLot;
import org.example.Ticket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void testParkingAtInitialLevelShouldReturnSlot() {
        // Arrange
        MultiLevelParkingLot multiLevelParkingLot = new MultiLevelParkingLot(2, 2);
        Car car = new Car("AB12FR1234", Color.WHITE);

        // Act
        Ticket actual = multiLevelParkingLot.park(car);

        // Assert
        assertEquals(new Ticket(0, 0), actual);
    }

    @Test
    void testCarIsParkedInMultiLevelCarParking() {
        // Arrange
        MultiLevelParkingLot multiLevelParkingLot = new MultiLevelParkingLot(2, 1);
        Car car1 = new Car("AB12FR1234", Color.WHITE);
        Car car2 = new Car("AA12FR1234", Color.WHITE);

        // Act
        multiLevelParkingLot.park(car1);
        multiLevelParkingLot.park(car2);
        boolean actual = multiLevelParkingLot.isCarParked(car2);

        // Assert
        assertTrue(actual);
    }

    @Test
    void testCarIsParkedInAnEmptySlotFromUnparkedLot() {
        // Arrange
        MultiLevelParkingLot multiLevelParkingLot = new MultiLevelParkingLot(2, 2);
        Car car1 = new Car("AB12FR1234", Color.WHITE);
        Car car2 = new Car("AA12FR1234", Color.WHITE);
        Car car3 = new Car("AA34FA1234", Color.GREEN);
        Car car4 = new Car("BB12IO9087", Color.WHITE);
        Car car = new Car("BC12IO9087", Color.WHITE);

        // Act
        multiLevelParkingLot.park(car1);
        multiLevelParkingLot.park(car2);
        Ticket parkingSlot = multiLevelParkingLot.park(car3);
        multiLevelParkingLot.park(car4);
        multiLevelParkingLot.unpark(parkingSlot, "AA34FA1234");
        Ticket actual = multiLevelParkingLot.park(car);

        // Assert
        assertEquals(parkingSlot, actual);
    }

    @Test
    void testCarIsNotParkedInMultiLevelCarParking() {
        // Arrange
        MultiLevelParkingLot multiLevelParkingLot = new MultiLevelParkingLot(2, 1);
        Car car1 = new Car("AB12FR1234", Color.WHITE);
        Car car2 = new Car("AA12FR1234", Color.WHITE);
        Car car = new Car("AA34FA1234", Color.GREEN);

        // Act
        multiLevelParkingLot.park(car1);
        multiLevelParkingLot.park(car2);
        boolean actual = multiLevelParkingLot.isCarParked(car);

        // Assert
        assertFalse(actual);
    }

    @Test
    void testCarCannotBeParkedInFullCapacity() {
        // Arrange
        MultiLevelParkingLot multiLevelParkingLot = new MultiLevelParkingLot(2, 1);
        Car car1 = new Car("AB12FR1234", Color.WHITE);
        Car car2 = new Car("AA12FR1234", Color.WHITE);
        Car car = new Car("AA34FA1234", Color.GREEN);

        // Act
        multiLevelParkingLot.park(car1);
        multiLevelParkingLot.park(car2);

        // Assert
        assertThrows(RuntimeException.class, () -> multiLevelParkingLot.park(car));
    }

    @Test
    void test3WhiteCarsArePresentInMultiLevelParkingLot() {
        // Arrange
        MultiLevelParkingLot multiLevelParkingLot = new MultiLevelParkingLot(2, 2);
        Car car1 = new Car("AB12FR1234", Color.WHITE);
        Car car2 = new Car("AA12FR1234", Color.WHITE);
        Car car3 = new Car("AA34FA1234", Color.GREEN);
        Car car4 = new Car("BB12IO9087", Color.WHITE);

        // Act
        multiLevelParkingLot.park(car1);
        multiLevelParkingLot.park(car2);
        multiLevelParkingLot.park(car3);
        multiLevelParkingLot.park(car4);
        int actual = multiLevelParkingLot.countCarsByColor(Color.WHITE);

        // Assert
        assertEquals(3, actual);
    }

    @Test
    void testUnparkingACarFromMultiLevelParkingLot() {
        // Arrange
        MultiLevelParkingLot multiLevelParkingLot = new MultiLevelParkingLot(2, 2);
        Car car1 = new Car("AB12FR1234", Color.WHITE);
        Car car2 = new Car("AA12FR1234", Color.WHITE);
        Car car3 = new Car("AA34FA1234", Color.GREEN);
        Car car4 = new Car("BB12IO9087", Color.WHITE);

        // Act
        multiLevelParkingLot.park(car1);
        multiLevelParkingLot.park(car2);
        Ticket parkedCarSlot = multiLevelParkingLot.park(car3);
        multiLevelParkingLot.park(car4);
        Car actual = multiLevelParkingLot.unpark(parkedCarSlot, "AA34FA1234");

        // Assert
        assertEquals(car3, actual);
    }
}
