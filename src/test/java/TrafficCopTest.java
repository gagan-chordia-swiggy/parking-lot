import org.example.*;
import org.example.enums.Color;
import org.example.enums.ParkingLotEvent;
import org.example.enums.ParkingStrategy;
import org.example.interfaces.ParkingLotSubscriber;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TrafficCopTest {
    @Test
    void testCopIsNotifiedWhenParkingIsFull() {
        // Arrange
        ParkingLotSubscriber observer = mock(TrafficCop.class);
        ParkingLot parkingLot1 = new ParkingLot(1, 0);
        ParkingLot parkingLot2 = new ParkingLot(1, 0);
        Car car = new Car("AB12BC1234", Color.GREEN);
        NotificationBus.instance().subscribe(observer, ParkingLotEvent.FULL);

        // Act
        parkingLot1.parkFromNearest(car, 0);
        parkingLot2.parkFromNearest(car, 0);

        // Assert
        verify(observer).notify(ParkingLotEvent.FULL, parkingLot1);
        verify(observer).notify(ParkingLotEvent.FULL, parkingLot2);
    }

    @Test
    void testCopIsNotifiedWhenParkingGetsVacant() {
        // Arrange
        ParkingLotSubscriber observer = mock(TrafficCop.class);
        ParkingLot parkingLot1 = new ParkingLot(1, 0);
        ParkingLot parkingLot2 = new ParkingLot(1, 0);
        Car car = new Car("AB12BC1234", Color.GREEN);
        NotificationBus.instance().subscribe(observer, ParkingLotEvent.EMPTY);

        // Act
        Ticket parkingTicket1 = parkingLot1.parkFromFarthest(car, 0);
        Ticket parkingTicket2 = parkingLot2.parkFromFarthest(car, 0);
        parkingLot1.unpark(parkingTicket1, "AB12BC1234");
        parkingLot2.unpark(parkingTicket2, "AB12BC1234");

        // Assert
        verify(observer).notify(ParkingLotEvent.EMPTY, parkingLot1);
        verify(observer).notify(ParkingLotEvent.EMPTY, parkingLot2);
    }

    @Test
    void testAllCopsAreNotifiedWhenParkingLotIsFull() {
        // Arrange
        TrafficCop cop1 = mock(TrafficCop.class);
        TrafficCop cop2 = mock(TrafficCop.class);
        ParkingLot lot = new ParkingLot(1, 0);
        Car car = new Car("AB12BC1234", Color.BLUE);
        NotificationBus bus = NotificationBus.instance();

        // Act
        bus.subscribe(cop1, ParkingLotEvent.FULL);
        bus.subscribe(cop2, ParkingLotEvent.FULL);
        lot.parkFromNearest(car, 0);

        // Assert
        verify(cop1).notify(ParkingLotEvent.FULL, lot);
        verify(cop2).notify(ParkingLotEvent.FULL, lot);
    }

    @Test
    void testAllCopsAreNotifiedWhenParkingLotGetsVacant() {
        // Arrange
        TrafficCop cop1 = mock(TrafficCop.class);
        TrafficCop cop2 = mock(TrafficCop.class);
        ParkingLot lot = new ParkingLot(1, 0);
        Car car = new Car("AB12BC1234", Color.BLUE);
        NotificationBus bus = NotificationBus.instance();

        // Act
        bus.subscribe(cop1, ParkingLotEvent.EMPTY);
        bus.subscribe(cop2, ParkingLotEvent.EMPTY);
        Ticket ticket = lot.parkFromNearest(car, 0);
        lot.unpark(ticket, car.registrationNumber());

        // Assert
        verify(cop1).notify(ParkingLotEvent.EMPTY, lot);
        verify(cop2).notify(ParkingLotEvent.EMPTY, lot);
    }
}
