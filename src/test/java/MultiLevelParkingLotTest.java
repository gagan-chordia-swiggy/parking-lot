import org.example.MultiLevelParkingLot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MultiLevelParkingLotTest {
    @Test
    void testMultiLevelParkingLotsAreCreated() {
        // Arrange, Act
        MultiLevelParkingLot multiLevelParkingLot = new MultiLevelParkingLot(2, 1);

        // Assert
        assertNotNull(multiLevelParkingLot);
    }
}
