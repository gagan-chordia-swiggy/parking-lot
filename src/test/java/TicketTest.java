import org.example.Ticket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TicketTest {
    @Test
    void testTicketIsCreated() {
        // Arrange, Act
        Ticket ticket = new Ticket(5, 3, "AB12BC1234");

        // Assert
        assertNotNull(ticket);
    }
}
