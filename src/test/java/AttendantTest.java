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
}
