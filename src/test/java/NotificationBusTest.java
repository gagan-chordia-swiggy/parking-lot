import org.example.NotificationBus;
import org.example.enums.ParkingLotEvent;
import org.example.interfaces.ParkingLotPublisher;
import org.example.interfaces.ParkingLotSubscriber;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class NotificationBusTest {
    @Test
    void testASubscriberIsNotifiedWhenParkingIsFull() {
        // Arrange
        NotificationBus bus = NotificationBus.instance();
        ParkingLotSubscriber subscriber = mock(ParkingLotSubscriber.class);
        ParkingLotPublisher publisher = mock(ParkingLotPublisher.class);

        // Act
        bus.subscribe(subscriber, ParkingLotEvent.FULL);
        bus.publish(publisher, ParkingLotEvent.FULL);

        // Assert
        verify(subscriber, times(1)).notify(ParkingLotEvent.FULL, publisher);
    }

    @Test
    void testASubscriberIsNotifiedWhenParkingLotBecameVacant() {
        // Arrange
        NotificationBus bus = NotificationBus.instance();
        ParkingLotSubscriber subscriber = mock(ParkingLotSubscriber.class);
        ParkingLotPublisher publisher = mock(ParkingLotPublisher.class);

        // Act
        bus.subscribe(subscriber, ParkingLotEvent.EMPTY);
        bus.publish(publisher, ParkingLotEvent.EMPTY);

        // Assert
        verify(subscriber, times(1)).notify(ParkingLotEvent.EMPTY, publisher);
    }

    @Test
    void testOnlyFullParkingEventSubscribersShouldBeNotified() {
        // Arrange
        NotificationBus bus = NotificationBus.instance();
        ParkingLotSubscriber subscriber1 = mock(ParkingLotSubscriber.class);
        ParkingLotSubscriber subscriber2 = mock(ParkingLotSubscriber.class);
        ParkingLotSubscriber subscriber3 = mock(ParkingLotSubscriber.class);
        ParkingLotPublisher publisher = mock(ParkingLotPublisher.class);

        // Act
        bus.subscribe(subscriber1, ParkingLotEvent.FULL);
        bus.subscribe(subscriber2, ParkingLotEvent.FULL);
        bus.subscribe(subscriber3, ParkingLotEvent.EMPTY);
        bus.publish(publisher, ParkingLotEvent.FULL);

        // Assert
        verify(subscriber1, times(1)).notify(ParkingLotEvent.FULL, publisher);
        verify(subscriber2, times(1)).notify(ParkingLotEvent.FULL, publisher);
        verify(subscriber3, never()).notify(ParkingLotEvent.FULL, publisher);
    }
}
