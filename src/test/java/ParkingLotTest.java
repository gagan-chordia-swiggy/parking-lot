import org.example.*;
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
    void testACarIsParkedFromNearestSlotInTheParkingLot() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(10);
        Car car1 = mock(Car.class);

        // Act
        Ticket actual = parkingLot.park(car1, 0, true);
        // Assert
        assertEquals(new Ticket(0, 1), actual);
    }

    @Test
    void testACarIsParkedFromFarthestSlotInTheParkingLot() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(10);
        Car car1 = mock(Car.class);

        // Act
        Ticket actual = parkingLot.park(car1, 0, false);
        // Assert
        assertEquals(new Ticket(0, 10), actual);
    }

    @Test
    void testACarIsNotPresentInTheParkingLot() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = mock(Car.class);

        // Act
        boolean actual = parkingLot.isCarParked(car);

        // Assert
        assertFalse(actual);
    }

    @Test
    void test2BlueCarsPresentInTheParkingLot() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(5);
        Car car1 = mock(Car.class);
        Car car2 = mock(Car.class);
        Car car3 = mock(Car.class);

        // Act
        when(car1.color()).thenReturn(Color.BLUE);
        when(car2.color()).thenReturn(Color.BLUE);
        when(car3.color()).thenReturn(Color.BLACK);
        parkingLot.park(car1, 0, true);
        parkingLot.park(car2, 0, true);
        parkingLot.park(car3, 0, true);

        int actual = parkingLot.countCarsByColor(Color.BLUE);

        // Assert
        assertEquals(2, actual);
    }

    @Test
    void test0BlackCarsPresentInTheParkingLot() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(5);
        Car car1 = mock(Car.class);
        Car car2 = mock(Car.class);
        Car car3 = mock(Car.class);

        // Act
        when(car1.color()).thenReturn(Color.BLUE);
        when(car2.color()).thenReturn(Color.BLUE);
        when(car3.color()).thenReturn(Color.BLUE);
        parkingLot.park(car1, 0, true);
        parkingLot.park(car2, 0, true);
        parkingLot.park(car3, 0, true);

        int actual = parkingLot.countCarsByColor(Color.BLACK);

        // Assert
        assertEquals(0, actual);
    }

    @Test
    void testParkingTheSameCarTwiceWithoutUnparkingThrowsException() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(3);
        Car car = mock(Car.class);

        // Act
        parkingLot.park(car, 0, true);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> parkingLot.park(car, 0, true));
    }

    @Test
    void testParkingACarInFullCapacityParkingLotThrowsException() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(1);
        Car car1 = mock(Car.class);
        Car car2 = mock(Car.class);

        // Act
        parkingLot.park(car1, 0, true);

        // Assert
        assertThrows(RuntimeException.class, () -> parkingLot.park(car2, 0, true));
    }

    @Test
    void testUnparkingACar() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(2);
        Car car = mock(Car.class);

        // Act
        when(car.registrationNumber()).thenReturn("AB12BC1234");
        Ticket ticket = parkingLot.park(car, 0, true);
        Car unparkedCar = parkingLot.unpark(ticket,"AB12BC1234");

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
        parkingLot.park(car1, 0, true);
        parkingLot.park(car2, 0, true);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> parkingLot.unpark(new Ticket(0, 1),"CB12AA1234"));
    }

    @Test
    void testParkingACarInUnparkedLot() {
        // Arrange
        ParkingLot parkingLot = new ParkingLot(2);
        Car car1 = mock(Car.class);
        Car car2 = mock(Car.class);
        Car car = mock(Car.class);

        // Act
        when(car1.registrationNumber()).thenReturn("AB12BC1234");
        Ticket unparkedLot = parkingLot.park(car1, 0, true);
        parkingLot.park(car2, 0, true);
        parkingLot.unpark(unparkedLot, "AB12BC1234");
        Ticket actual = parkingLot.park(car, 0, true);

        // Assert
        assertEquals(unparkedLot, actual);
    }

    @Test
    void testObserverIsNotifiedWhenParkingIsFull() {
        // Arrange
        ParkingLotSubscriber observer = mock(Attendant.class);
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car("AB12BC1234", Color.GREEN);

        NotificationBus.instance().subscribe(observer, ParkingLotEvent.FULL);
        parkingLot.parkFromNearest(car, 0);

        verify(observer).notify(ParkingLotEvent.FULL, parkingLot);
    }
}
