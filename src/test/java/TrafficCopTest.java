import org.example.*;
import org.example.interfaces.ParkingLotSubscriber;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TrafficCopTest {
    @Test
    void testObserverIsNotifiedWhenParkingIsFull() {
        // Arrange
        ParkingLotSubscriber observer = mock(TrafficCop.class);
        ParkingLot parkingLot1 = new ParkingLot(1);
        ParkingLot parkingLot2 = new ParkingLot(1);
        Car car = new Car("AB12BC1234", Color.GREEN);
        NotificationBus.instance().subscribe(observer, ParkingLotEvent.FULL);

        // Act
        parkingLot1.parkFromNearest(car, 0);
        parkingLot2.parkFromFarthest(car, 0);

        // Assert
        verify(observer).notify(ParkingLotEvent.FULL, parkingLot1);
        verify(observer).notify(ParkingLotEvent.FULL, parkingLot2);
    }

    @Test
    void testObserverIsNotifiedWhenParkingGetsVacant() {
        // Arrange
        ParkingLotSubscriber observer = mock(Attendant.class);
        ParkingLot parkingLot1 = new ParkingLot(1);
        ParkingLot parkingLot2 = new ParkingLot(1);
        Car car = new Car("AB12BC1234", Color.GREEN);
        NotificationBus.instance().subscribe(observer, ParkingLotEvent.EMPTY);

        // Act
        Ticket parkingTicket1 = parkingLot1.parkFromNearest(car, 0);
        Ticket parkingTicket2 = parkingLot2.parkFromFarthest(car, 0);
        parkingLot1.unpark(parkingTicket1, "AB12BC1234");
        parkingLot2.unpark(parkingTicket2, "AB12BC1234");

        // Assert
        verify(observer).notify(ParkingLotEvent.EMPTY, parkingLot1);
        verify(observer).notify(ParkingLotEvent.EMPTY, parkingLot2);
    }
}
