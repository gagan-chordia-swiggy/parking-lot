import org.example.*;
import org.example.exceptions.InvalidAttendantException;
import org.example.exceptions.ParkingLotFullException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class AttendantTest {
    @Test
    void testAttendantIsCreated() {
        // Arrange, Act
        Attendant attendant = new Attendant("gagan", true);

        // Assert
        assertNotNull(attendant);
    }

    @Test
    void testAttendantWithoutNameThrowsException() {
        // Assert
        assertThrows(InvalidAttendantException.class, () -> new Attendant(null, false));
    }

    @Test
    void testAttendantIsAssignedAParkingLot() {
        // Arrange
        Attendant attendant = new Attendant("Abc", true);
        ParkingLot parkingLot = mock(ParkingLot.class);

        // Act
        attendant.add(parkingLot);

        // Assert
        assertEquals(1, attendant.parkingLots().size());
    }

    @Test
    void testAttendantParksCarNearInParkingLot() {
        // Arrange
        Attendant attendant = new Attendant("Abc", true);
        ParkingLot parkingLot = new ParkingLot(7);
        Car car = new Car("AA12BC3456", Color.BLUE);

        // Act
        attendant.add(parkingLot);
        Ticket actual = attendant.park(car);

        // Assert
        assertEquals(new Ticket(0, 0), actual);
    }

    @Test
    void testAttendantParksCarFarInParkingLot() {
        // Arrange
        Attendant attendant = new Attendant("Abc", false);
        ParkingLot parkingLot = new ParkingLot(7);
        Car car = new Car("AA12BC3456", Color.BLUE);

        // Act
        attendant.add(parkingLot);
        Ticket actual = attendant.park(car);

        // Assert
        assertEquals(new Ticket(0, 7), actual);
    }

    @Test
    void testAttendantParksAt2ndParkingLot() {
        // Arrange
        Attendant attendant = new Attendant("abc", true);
        ParkingLot parkingLot1 = new ParkingLot(1);
        ParkingLot parkingLot2 = new ParkingLot(1);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);

        // Act
        attendant.add(parkingLot1);
        attendant.add(parkingLot2);
        attendant.park(car1);
        Ticket actual = attendant.park(car2);

        // Assert
        assertEquals(new Ticket(1, 0), actual);
    }

    @Test
    void test2AttendantsCanParkAtSameParkingLot() {
        // Arrange
        Attendant attendant1 = new Attendant("abc", true);
        Attendant attendant2 = new Attendant("def", true);
        ParkingLot parkingLot = new ParkingLot(3);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);

        // Act
        attendant1.add(parkingLot);
        attendant2.add(parkingLot);
        attendant1.park(car1);
        attendant2.park(car2);

        // Assert
        assertEquals(2, parkingLot.countCarsByColor(Color.WHITE));
    }

    @Test
    void testAttendantParksAtFullParkingLotThrowsException() {
        // Arrange
        Attendant attendant = new Attendant("abc", true);
        ParkingLot parkingLot1 = new ParkingLot(1);
        ParkingLot parkingLot2 = new ParkingLot(1);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);
        Car car3 = new Car("AA12DD3214", Color.GREEN);

        // Act
        attendant.add(parkingLot1);
        attendant.add(parkingLot2);
        attendant.park(car1);
        attendant.park(car2);

        // Assert
        assertThrows(ParkingLotFullException.class, () -> attendant.park(car3));
    }

    @Test
    void testAttendantCountsCarByColor() {
        // Arrange
        Attendant attendant = new Attendant("abc", true);
        ParkingLot parkingLot1 = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(2);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);
        Car car3 = new Car("AA12DD3214", Color.GREEN);

        // Act
        attendant.add(parkingLot1);
        attendant.add(parkingLot2);
        attendant.park(car1);
        attendant.park(car2);
        attendant.park(car3);
        int actual = attendant.countCarsByColor(Color.WHITE);

        // Assert
        assertEquals(2, actual);
    }

    @Test
    void testAttendantCheckIfCarIsParked() {
        // Arrange
        Attendant attendant = new Attendant("abc", true);
        ParkingLot parkingLot1 = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(2);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);
        Car car3 = new Car("AA12DD3214", Color.GREEN);

        // Act
        attendant.add(parkingLot1);
        attendant.add(parkingLot2);
        attendant.park(car1);
        attendant.park(car2);
        attendant.park(car3);
        boolean actual = attendant.isCarParked(car2);

        // Assert
        assertTrue(actual);
    }

    @Test
    void testAttendantCheckIfCarIsNotParked() {
        // Arrange
        Attendant attendant = new Attendant("abc", true);
        ParkingLot parkingLot1 = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(2);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);
        Car car3 = new Car("AA12DD3214", Color.GREEN);

        // Act
        attendant.add(parkingLot1);
        attendant.add(parkingLot2);
        attendant.park(car1);
        attendant.park(car3);
        boolean actual = attendant.isCarParked(car2);

        // Assert
        assertFalse(actual);
    }

    @Test
    void testAttendantUnparkingACar() {
        // Arrange
        Attendant attendant = new Attendant("abc", true);
        ParkingLot parkingLot1 = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(2);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);
        Car car3 = new Car("AA12DD3214", Color.GREEN);

        // Act
        attendant.add(parkingLot1);
        attendant.add(parkingLot2);
        attendant.park(car1);
        Ticket parkingTicket = attendant.park(car2);
        attendant.park(car3);
        Car actual = attendant.unpark(parkingTicket, "AB12FE2345");

        // Assert
        assertEquals(car2, actual);
    }
}
