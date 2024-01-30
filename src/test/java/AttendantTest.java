import org.example.Attendant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AttendantTest {
    @Test
    void testAttendantIsCreated() {
        // Arrange, Act
        Attendant attendant = new Attendant("gagan");

        // Assert
        assertNotNull(attendant);
    }
}
