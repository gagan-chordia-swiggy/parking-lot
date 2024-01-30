import org.example.Attendant;
import org.example.exceptions.InvalidAttendantException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}
