import org.example.exceptions.InvalidTicketException;
import org.example.Ticket;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TicketTest {
    @Test
    void testTicketIsCreated() {
        // Arrange, Act
        Ticket ticket = new Ticket(5, 3, 12);

        // Assert
        assertNotNull(ticket);
    }

    @Test
    void testNegativeLevelTicketThrowsException() {
        // Assert
        assertThrows(InvalidTicketException.class, () -> new Ticket(-1, 3, 7));
    }

    @Test
    void testNegativeSlotTicketThrowsException() {
        // Assert
        assertThrows(InvalidTicketException.class, () -> new Ticket(1, -3, 8));
    }
}
