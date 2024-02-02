import org.example.*;
import org.example.enums.Color;
import org.example.enums.ParkingLotEvent;
import org.example.exceptions.InvalidAttendantException;
import org.example.enums.ParkingStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AttendantTest {
    @Test
    void testAttendantIsCreated() {
        // Arrange, Act
        Attendant attendant = new Attendant("gagan", ParkingStrategy.NEAREST);

        // Assert
        assertNotNull(attendant);
    }

    @Test
    void testAttendantWithoutNameThrowsException() {
        // Assert
        assertThrows(InvalidAttendantException.class, () -> new Attendant(null, ParkingStrategy.NEAREST));
    }

    @Test
    void testAttendantIsAssignedAParkingLot() {
        // Arrange
        Attendant attendant = new Attendant("Abc", ParkingStrategy.NEAREST);
        ParkingLot parkingLot = mock(ParkingLot.class);

        // Act
        attendant.add(parkingLot);

        // Assert
        assertEquals(1, attendant.parkingLots().size());
    }

    @Test
    void testAttendantParksInNearestPositionInParkingLot() {
        // Arrange
        Attendant attendant = new Attendant("Abc", ParkingStrategy.NEAREST);
        ParkingLot parkingLot = new ParkingLot(7, 3);
        Car car = new Car("AA12BC3456", Color.BLUE);

        // Act
        attendant.add(parkingLot);
        Ticket actual = attendant.park(car);

        // Assert
        assertEquals(new Ticket(0, 1,3), actual);
    }

    @Test
    void testAttendantParksCarFarInParkingLot() {
        // Arrange
        Attendant attendant = new Attendant("Abc", ParkingStrategy.FARTHEST);
        ParkingLot parkingLot = new ParkingLot(7, 2);
        Car car = new Car("AA12BC3456", Color.BLUE);

        // Act
        attendant.add(parkingLot);
        Ticket actual = attendant.park(car);

        // Assert
        assertEquals(new Ticket(0, 7, 2), actual);
    }

    @Test
    void testAttendantParksCarDistributively() {
        // Arrange
        Attendant attendant = new Attendant("Abc", ParkingStrategy.DISTRIBUTED);
        ParkingLot parkingLot1 = new ParkingLot(4, 5);
        ParkingLot parkingLot2 = new ParkingLot(4, 8);
        Car car1 = new Car("FS12ER1234", Color.GREEN);
        Car car2 = new Car("AS12ER1234", Color.GREEN);

        // Act
        attendant.add(parkingLot1);
        attendant.add(parkingLot2);
        Ticket parkingTicket1 = attendant.park(car1);
        Ticket parkingTicket2 = attendant.park(car2);

        // Assert
        assertEquals(new Ticket(0, 1, 5), parkingTicket1);
        assertEquals(new Ticket(1, 1, 8), parkingTicket2);
    }

    @Test
    void testAttendantParksAt2ndParkingLot() {
        // Arrange
        Attendant attendant = new Attendant("abc", ParkingStrategy.NEAREST);
        ParkingLot parkingLot1 = new ParkingLot(1, 0);
        ParkingLot parkingLot2 = new ParkingLot(1, 0);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);

        // Act
        attendant.add(parkingLot1);
        attendant.add(parkingLot2);
        attendant.park(car1);
        Ticket actual = attendant.park(car2);

        // Assert
        assertEquals(new Ticket(1, 1, 0), actual);
    }

    @Test
    void test2AttendantsCanParkAtSameParkingLot() {
        // Arrange
        Attendant attendant1 = new Attendant("abc", ParkingStrategy.NEAREST);
        Attendant attendant2 = new Attendant("def", ParkingStrategy.FARTHEST);
        ParkingLot parkingLot = new ParkingLot(3, 0);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);

        // Act
        attendant1.add(parkingLot);
        attendant2.add(parkingLot);
        attendant1.park(car1);
        attendant2.park(car2);

        // Assert
        assertEquals(2, parkingLot.countCarsByColor(Color.WHITE));
    }

    @Test
    void testAttendantCountsCarByColor() {
        // Arrange
        Attendant attendant = new Attendant("abc", ParkingStrategy.NEAREST);
        ParkingLot parkingLot1 = new ParkingLot(2, 0);
        ParkingLot parkingLot2 = new ParkingLot(2, 0);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);
        Car car3 = new Car("AA12DD3214", Color.GREEN);

        // Act
        attendant.add(parkingLot1);
        attendant.add(parkingLot2);
        attendant.park(car1);
        attendant.park(car2);
        attendant.park(car3);
        int actual = attendant.countCarsByColor(Color.WHITE);

        // Assert
        assertEquals(2, actual);
    }

    @Test
    void testAttendantUnparkingACar() {
        // Arrange
        Attendant attendant = new Attendant("abc", ParkingStrategy.FARTHEST);
        ParkingLot parkingLot1 = new ParkingLot(2, 0);
        ParkingLot parkingLot2 = new ParkingLot(2, 0);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);
        Car car3 = new Car("AA12DD3214", Color.GREEN);

        // Act
        attendant.add(parkingLot1);
        attendant.add(parkingLot2);
        attendant.park(car1);
        Ticket parkingTicket = attendant.park(car2);
        attendant.park(car3);
        Car actual = attendant.unpark(parkingTicket, "AB12FE2345");

        // Assert
        assertEquals(car2, actual);
    }

    @Test
    void testAsAttendantChangeParkingStrategy() {
        // Arrange
        Attendant attendant = new Attendant("abc", ParkingStrategy.FARTHEST);
        ParkingLot parkingLot1 = new ParkingLot(2, 0);
        ParkingLot parkingLot2 = new ParkingLot(2, 0);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);

        // Act
        attendant.add(parkingLot1);
        attendant.add(parkingLot2);
        Ticket parkingTicket = attendant.park(car1);
        attendant.setParkingStrategy(ParkingStrategy.NEAREST);
        Ticket actual = attendant.park(car2);

        // Assert
        assertEquals(new Ticket(1, 2, 0), parkingTicket);
        assertEquals(new Ticket(0, 1, 0), actual);
    }

    @Test
    void testAllAttendantsAreNotifiedWhenTheParkingLotIsFull() {
        // Arrange
        Attendant attendant1 = spy(new Attendant("abc", ParkingStrategy.DISTRIBUTED));
        Attendant attendant2 = spy(new Attendant("def", ParkingStrategy.NEAREST));
        ParkingLot parkingLot = new ParkingLot(1, 4);
        Car car = new Car("AB12WE2345", Color.WHITE);
        NotificationBus bus = NotificationBus.instance();

        // Act
        attendant1.add(parkingLot);
        attendant2.add(parkingLot);
        bus.subscribe(attendant1, ParkingLotEvent.FULL);
        bus.subscribe(attendant2, ParkingLotEvent.FULL);
        attendant1.park(car);

        // Assert
        verify(attendant1).notify(ParkingLotEvent.FULL, parkingLot);
        verify(attendant2).notify(ParkingLotEvent.FULL, parkingLot);
    }

    @Test
    void testAllAttendantsAreNotifiedWhenTheParkingLotIsVacant() {
        // Arrange
        Attendant attendant1 = spy(new Attendant("abc", ParkingStrategy.DISTRIBUTED));
        Attendant attendant2 = spy(new Attendant("def", ParkingStrategy.NEAREST));
        ParkingLot parkingLot = new ParkingLot(1, 6);
        Car car = new Car("AB12WE2345", Color.WHITE);
        NotificationBus bus = NotificationBus.instance();

        // Act
        attendant1.add(parkingLot);
        attendant2.add(parkingLot);
        bus.subscribe(attendant1, ParkingLotEvent.EMPTY);
        bus.subscribe(attendant2, ParkingLotEvent.EMPTY);
        Ticket ticket = attendant1.park(car);
        attendant2.unpark(ticket, car.registrationNumber());

        // Assert
        verify(attendant1).notify(ParkingLotEvent.EMPTY, parkingLot);
        verify(attendant2).notify(ParkingLotEvent.EMPTY, parkingLot);
    }

//    @Test
//    void testAttendantIsNotifiedOnlyAboutAssignedParkingLotWhenFull() {
//        // Arrange
//        Attendant attendant = spy(new Attendant("abc", ParkingStrategy.NEAREST));
//        ParkingLot parkingLot1 = new ParkingLot(1);
//        ParkingLot parkingLot2 = new ParkingLot(1);
//        Car car = new Car("TT11YY6677", Color.GREEN);
//        NotificationBus.instance().subscribe(attendant, ParkingLotEvent.FULL);
//
//        // Act
//        attendant.add(parkingLot1);
//        parkingLot1.park(car, 2, ParkingStrategy.FARTHEST);
//        parkingLot2.park(car,2, ParkingStrategy.FARTHEST);
//
//        // Assert
//        verify(attendant).notify(ParkingLotEvent.FULL, parkingLot1);
//        verify(attendant, never()).notify(ParkingLotEvent.FULL, parkingLot2);
//    }
//
//    @Test
//    void testAttendantIsNotifiedOnlyAboutAssignedParkingLotGetsVacant() {
//        // Arrange
//        Attendant attendant = spy(new Attendant("abc", ParkingStrategy.NEAREST));
//        ParkingLot parkingLot1 = new ParkingLot(1);
//        ParkingLot parkingLot2 = new ParkingLot(1);
//        Car car = new Car("TT11YY6677", Color.GREEN);
//        NotificationBus.instance().subscribe(attendant, ParkingLotEvent.EMPTY);
//
//        // Act
//        attendant.add(parkingLot1);
//        Ticket ticket1 = parkingLot1.park(car, 2, ParkingStrategy.FARTHEST);
//        Ticket ticket2 = parkingLot2.park(car, 2, ParkingStrategy.FARTHEST);
//        parkingLot1.unpark(ticket1, "TT11YY6677");
//        parkingLot2.unpark(ticket2, "TT11YY6677");
//
//        // Assert
//        verify(attendant).notify(ParkingLotEvent.EMPTY, parkingLot1);
//        verify(attendant, never()).notify(ParkingLotEvent.EMPTY, parkingLot2);
//    }
}
