import org.example.*;
import org.example.enums.Color;
import org.example.enums.ParkingLotEvent;
import org.example.enums.ParkingStrategy;
import org.example.interfaces.ParkingLotSubscriber;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ParkingLotTest {
    @Test
    void testParkingLotIsCreated() {
        // Arrange, Act
        ParkingLot parkingLot = new ParkingLot(12, 1);

        // Assert
        assertNotNull(parkingLot);
    }

    @Test
    void testParkingLotWithZeroLotsThrowsException() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(0, 3));
    }

    @Test
    void testParkingLotWithNegative5LotsThrowsException() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(-5, 3));
    }

    @Test
    void testACarIsParkedFromNearestSlotInTheParkingLot() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(10, 1);
        Car car1 = mock(Car.class);

        // Act
        Ticket actual = parkingLot.parkFromNearest(car1, 0);
        // Assert
        assertEquals(new Ticket(0, 1, 1), actual);
    }

    @Test
    void testACarIsParkedFromFarthestSlotInTheParkingLot() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(10, 12);
        Car car1 = mock(Car.class);

        // Act
        Ticket actual = parkingLot.parkFromFarthest(car1, 0);
        // Assert
        assertEquals(new Ticket(0, 10, 12), actual);
    }

    @Test
    void testACarIsNotPresentInTheParkingLot() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(1, 8);
        Car car = mock(Car.class);

        // Act
        boolean actual = parkingLot.isCarParked(car);

        // Assert
        assertFalse(actual);
    }

    @Test
    void test2BlueCarsPresentInTheParkingLot() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(5, 6);
        Car car1 = mock(Car.class);
        Car car2 = mock(Car.class);
        Car car3 = mock(Car.class);

        // Act
        when(car1.color()).thenReturn(Color.BLUE);
        when(car2.color()).thenReturn(Color.BLUE);
        when(car3.color()).thenReturn(Color.BLACK);
        parkingLot.parkFromNearest(car1, 0);
        parkingLot.parkFromNearest(car2, 0);
        parkingLot.parkFromNearest(car3, 0);

        int actual = parkingLot.countCarsByColor(Color.BLUE);

        // Assert
        assertEquals(2, actual);
    }

    @Test
    void test0BlackCarsPresentInTheParkingLot() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(5, 3);
        Car car1 = mock(Car.class);
        Car car2 = mock(Car.class);
        Car car3 = mock(Car.class);

        // Act
        when(car1.color()).thenReturn(Color.BLUE);
        when(car2.color()).thenReturn(Color.BLUE);
        when(car3.color()).thenReturn(Color.BLUE);
        parkingLot.parkFromNearest(car1, 0);
        parkingLot.parkFromNearest(car2, 0);
        parkingLot.parkFromNearest(car3, 0);

        int actual = parkingLot.countCarsByColor(Color.BLACK);

        // Assert
        assertEquals(0, actual);
    }

    @Test
    void testParkingTheSameCarTwiceWithoutUnparkingThrowsException() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(3, 5);
        Car car = mock(Car.class);

        // Act
        parkingLot.parkFromNearest(car, 0);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> parkingLot.parkFromNearest(car, 0));
    }

    @Test
    void testParkingACarInFullCapacityParkingLotThrowsException() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(1, 7);
        Car car1 = mock(Car.class);
        Car car2 = mock(Car.class);

        // Act
        parkingLot.parkFromNearest(car1, 0);

        // Assert
        assertThrows(RuntimeException.class, () -> parkingLot.parkFromNearest(car2, 0));
    }

    @Test
    void testUnparkingACar() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(2, 5);
        Car car = mock(Car.class);

        // Act
        when(car.registrationNumber()).thenReturn("AB12BC1234");
        Ticket ticket = parkingLot.parkFromNearest(car, 0);
        Car unparkedCar = parkingLot.unpark(ticket,"AB12BC1234");

        // Assert
        assertEquals(car, unparkedCar);
    }

    @Test
    void testUnparkingACarNotPresentThrowsException() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(2,2);
        Car car1 = new Car("AB12BC1234", Color.RED);
        Car car2 = new Car("AA12BC1234", Color.RED);


        // Act
        parkingLot.parkFromNearest(car1, 0);
        parkingLot.parkFromNearest(car2, 0);

        // Assert
        assertThrows(IllegalArgumentException.class,
                () -> parkingLot.unpark(new Ticket(0, 1, 2),"CB12AA1234"));
    }

    @Test
    void testParkingACarInUnparkedLot() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(2, 7);
        Car car1 = mock(Car.class);
        Car car2 = mock(Car.class);
        Car car = mock(Car.class);

        // Act
        when(car1.registrationNumber()).thenReturn("AB12BC1234");
        Ticket unparkedLot = parkingLot.parkFromNearest(car1, 0);
        parkingLot.parkFromNearest(car2, 0);
        parkingLot.unpark(unparkedLot, "AB12BC1234");
        Ticket actual = parkingLot.parkFromNearest(car, 0);

        // Assert
        assertEquals(unparkedLot, actual);
    }
}
