import org.example.Assignment;
import org.example.Attendant;
import org.example.ParkingLot;
import org.example.exceptions.InvalidAttendantException;
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
}
