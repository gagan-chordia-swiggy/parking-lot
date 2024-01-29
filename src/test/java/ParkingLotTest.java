import org.example.Car;
import org.example.Color;
import org.example.ParkingLot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testParkingLotWithZeroLotsThrowsException() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(0));
    }

    @Test
    void testParkingLotWithNegative5LotsThrowsException() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(-5));
    }

    @Test
    void testParkingLotWithNegative1LotsThrowsException() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(-1));
    }

    @Test
    void testACarIsParkedInTheParkingLot() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(10);
        Car car = new Car("AP00AD1234", Color.BLUE);

        // Act
        String actual = parkingLot.park(car);

        // Assert
        assertEquals("1", actual);
    }

    @Test
    void testACarIsNotPresentInTheParkingLot() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car("AP00AD1234", Color.BLUE);

        // Act
        boolean actual = parkingLot.isCarParked(car);

        // Assert
        assertFalse(actual);
    }

    @Test
    void test2BlueCarsPresentInTheParkingLot() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(5);
        Car car1 = new Car("AB12CD1234", Color.BLUE);
        Car car2 = new Car("AB12CD1224", Color.BLUE);
        Car car3 = new Car("AB02CD1234", Color.BLACK);

        // Act
        parkingLot.park(car1);
        parkingLot.park(car2);
        parkingLot.park(car3);

        int actual = parkingLot.countCarsByColor(Color.BLUE);

        // Assert
        assertEquals(2, actual);
    }

    @Test
    void test0BlackCarsPresentInTheParkingLot() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(5);
        Car car1 = new Car("AB12CD1234", Color.BLUE);
        Car car2 = new Car("AB12CD1224", Color.BLUE);
        Car car3 = new Car("AB02CD1234", Color.GREEN);

        // Act
        parkingLot.park(car1);
        parkingLot.park(car2);
        parkingLot.park(car3);

        int actual = parkingLot.countCarsByColor(Color.BLACK);

        // Assert
        assertEquals(0, actual);
    }

    @Test
    void testParkingTheSameCarTwiceWithoutUnparkingThrowsException() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(3);
        Car car = new Car("AB12CD1234", Color.BLUE);

        // Act
        parkingLot.park(car);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> parkingLot.park(car));
    }

    @Test
    void testParkingACarInFullCapacityParkingLotThrowsException() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(1);
        Car car1 = new Car("AB12CD1234", Color.BLUE);
        Car car2 = new Car("AA12CD1234", Color.BLACK);

        // Act
        parkingLot.park(car1);

        // Assert
        assertThrows(RuntimeException.class, () -> parkingLot.park(car2));
    }

    @Test
    void testUnparkingACar() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(2);
        Car car = new Car("AB12BC1234", Color.RED);

        // Act
        String slotNumber = parkingLot.park(car);
        Car unparkedCar = parkingLot.unpark(slotNumber,"AB12BC1234");

        // Assert
        assertEquals(car, unparkedCar);
    }

    @Test
    void testUnparkingACarNotPresentThrowsException() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(2);
        Car car1 = new Car("AB12BC1234", Color.RED);
        Car car2 = new Car("AA12BC1234", Color.RED);

        // Act
        parkingLot.park(car1);
        parkingLot.park(car2);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> parkingLot.unpark("2","CB12AA1234"));
    }

    @Test
    void testParkingACarInUnparkedLot() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(2);
        Car car1 = new Car("AB12BC1234", Color.RED);
        Car car2 = new Car("AA12BC1234", Color.RED);
        Car car = new Car("CB12AA1234", Color.RED);

        // Act
        parkingLot.park(car1);
        parkingLot.park(car2);
        parkingLot.unpark("1", "AB12BC1234");
        parkingLot.park(car);

        boolean actual = parkingLot.isCarParked(car);

        // Assert
        assertTrue(actual);
    }
}
