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
        Attendant attendant = new Attendant("gagan");

        // Assert
        assertNotNull(attendant);
    }

    @Test
    void testAttendantWithoutNameThrowsException() {
        // Assert
        assertThrows(InvalidAttendantException.class, () -> new Attendant(null));
    }

    @Test
    void testAttendantIsAssignedAParkingLot() {
        // Arrange
        Attendant attendant = new Attendant("Abc");
        Assignment assignment = new Assignment(mock(ParkingLot.class), attendant);

        // Act
        attendant.add(assignment);

        // Assert
        assertEquals(1, attendant.assignments().size());
    }

    @Test
    void testParkingLotCannotBeAssignedToUnknownAttendantThrowsException() {
        // Arrange
        Attendant attendant = new Attendant("Abc");
        Assignment assignment = mock(Assignment.class);

        // Assert
        assertThrows(InvalidAttendantException.class, () -> attendant.add(assignment));
    }

    @Test
    void testAttendantParksAt2ndParkingLot() {
        // Arrange
        Attendant attendant = new Attendant("abc");
        Assignment assignment1 = new Assignment(new ParkingLot(1), attendant);
        Assignment assignment2 = new Assignment(new ParkingLot(1), attendant);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);

        // Act
        attendant.add(assignment1);
        attendant.add(assignment2);
        attendant.park(car1, 0);
        Ticket actual = attendant.park(car2, 1);

        // Assert
        assertEquals(new Ticket(1, 1), actual);
    }

    @Test
    void test2AttendantsCanParkAtSameParkingLot() {
        // Arrange
        Attendant attendant1 = new Attendant("abc");
        Attendant attendant2 = new Attendant("def");
        ParkingLot parkingLot = new ParkingLot(3);
        Assignment assignment1 = new Assignment(parkingLot, attendant1);
        Assignment assignment2 = new Assignment(parkingLot, attendant2);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);

        // Act
        attendant1.add(assignment1);
        attendant2.add(assignment2);
        attendant1.park(car1, 0);
        attendant2.park(car2, 0);

        // Assert
        assertEquals(2, parkingLot.countCarsByColor(Color.WHITE));
    }

    @Test
    void testAttendantParksAtFullParkingLotThrowsException() {
        // Arrange
        Attendant attendant = new Attendant("abc");
        Assignment assignment1 = new Assignment(new ParkingLot(1), attendant);
        Assignment assignment2 = new Assignment(new ParkingLot(1), attendant);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);
        Car car3 = new Car("AA12DD3214", Color.GREEN);

        // Act
        attendant.add(assignment1);
        attendant.add(assignment2);
        attendant.park(car1, 0);
        attendant.park(car2, 1);

        // Assert
        assertThrows(ParkingLotFullException.class, () -> attendant.park(car3, 1));
    }

    @Test
    void testAttendantCountsCarByColor() {
        // Arrange
        Attendant attendant = new Attendant("abc");
        Assignment assignment1 = new Assignment(new ParkingLot(2), attendant);
        Assignment assignment2 = new Assignment(new ParkingLot(2), attendant);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);
        Car car3 = new Car("AA12DD3214", Color.GREEN);

        // Act
        attendant.add(assignment1);
        attendant.add(assignment2);
        attendant.park(car1, 0);
        attendant.park(car2, 0);
        attendant.park(car3, 0);
        int actual = attendant.countCarsByColor(Color.WHITE);

        // Assert
        assertEquals(2, actual);
    }

    @Test
    void testAttendantCheckIfCarIsParked() {
        // Arrange
        Attendant attendant = new Attendant("abc");
        Assignment assignment1 = new Assignment(new ParkingLot(2), attendant);
        Assignment assignment2 = new Assignment(new ParkingLot(2), attendant);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);
        Car car3 = new Car("AA12DD3214", Color.GREEN);

        // Act
        attendant.add(assignment1);
        attendant.add(assignment2);
        attendant.park(car1, 0);
        attendant.park(car2, 0);
        attendant.park(car3, 0);
        boolean actual = attendant.isCarParked(car2);

        // Assert
        assertTrue(actual);
    }

    @Test
    void testAttendantCheckIfCarIsNotParked() {
        // Arrange
        Attendant attendant = new Attendant("abc");
        Assignment assignment1 = new Assignment(new ParkingLot(2), attendant);
        Assignment assignment2 = new Assignment(new ParkingLot(2), attendant);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);
        Car car3 = new Car("AA12DD3214", Color.GREEN);

        // Act
        attendant.add(assignment1);
        attendant.add(assignment2);
        attendant.park(car1, 0);
        attendant.park(car3, 0);
        boolean actual = attendant.isCarParked(car2);

        // Assert
        assertFalse(actual);
    }

    @Test
    void testAttendantUnparkingACar() {
        // Arrange
        Attendant attendant = new Attendant("abc");
        Assignment assignment1 = new Assignment(new ParkingLot(2), attendant);
        Assignment assignment2 = new Assignment(new ParkingLot(2), attendant);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);
        Car car3 = new Car("AA12DD3214", Color.GREEN);

        // Act
        attendant.add(assignment1);
        attendant.add(assignment2);
        attendant.park(car1, 0);
        Ticket parkingTicket = attendant.park(car2, 0);
        attendant.park(car3, 0);
        Car actual = attendant.unpark(parkingTicket, "AB12FE2345");

        // Assert
        assertEquals(car2, actual);
    }
}
