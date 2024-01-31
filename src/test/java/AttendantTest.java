import org.example.*;
import org.example.exceptions.InvalidAttendantException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class AttendantTest {
    @Test
    void testAttendantIsCreated() {
        // Arrange, Act
        Attendant attendant = new Attendant("gagan", new NearestParkingStrategy());

        // Assert
        assertNotNull(attendant);
    }

    @Test
    void testAttendantWithoutNameThrowsException() {
        // Assert
        assertThrows(InvalidAttendantException.class, () -> new Attendant(null, new NearestParkingStrategy()));
    }

    @Test
    void testAttendantIsAssignedAParkingLot() {
        // Arrange
        Attendant attendant = new Attendant("Abc", new NearestParkingStrategy());
        ParkingLot parkingLot = mock(ParkingLot.class);

        // Act
        attendant.add(parkingLot);

        // Assert
        assertEquals(1, attendant.parkingLots().size());
    }

    @Test
    void testAttendantParksCarNearInParkingLot() {
        // Arrange
        Attendant attendant = new Attendant("Abc", new NearestParkingStrategy());
        ParkingLot parkingLot = new ParkingLot(7);
        Car car = new Car("AA12BC3456", Color.BLUE);

        // Act
        attendant.add(parkingLot);
        Ticket actual = attendant.park(car);

        // Assert
        assertEquals(new Ticket(0, 1), actual);
    }

    @Test
    void testAttendantParksCarFarInParkingLot() {
        // Arrange
        Attendant attendant = new Attendant("Abc", new FarthestParkingStrategy());
        ParkingLot parkingLot = new ParkingLot(7);
        Car car = new Car("AA12BC3456", Color.BLUE);

        // Act
        attendant.add(parkingLot);
        Ticket actual = attendant.park(car);

        // Assert
        assertEquals(new Ticket(0, 7), actual);
    }

    @Test
    void testAttendantParksCarDistributively() {
        // Arrange
        Attendant attendant = new Attendant("Abc", new DistributedStrategy());
        ParkingLot parkingLot1 = new ParkingLot(4);
        ParkingLot parkingLot2 = new ParkingLot(4);
        Car car1 = new Car("FS12ER1234", Color.GREEN);
        Car car2 = new Car("AS12ER1234", Color.GREEN);

        // Act
        attendant.add(parkingLot1);
        attendant.add(parkingLot2);
        Ticket parkingTicket1 = attendant.park(car1);
        Ticket parkingTicket2 = attendant.park(car2);

        // Assert
        assertEquals(new Ticket(0, 1), parkingTicket1);
        assertEquals(new Ticket(1, 1), parkingTicket2);
    }

    @Test
    void testAttendantParksAt2ndParkingLot() {
        // Arrange
        Attendant attendant = new Attendant("abc", new NearestParkingStrategy());
        ParkingLot parkingLot1 = new ParkingLot(1);
        ParkingLot parkingLot2 = new ParkingLot(1);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);

        // Act
        attendant.add(parkingLot1);
        attendant.add(parkingLot2);
        attendant.park(car1);
        Ticket actual = attendant.park(car2);

        // Assert
        assertEquals(new Ticket(1, 1), actual);
    }

    @Test
    void test2AttendantsCanParkAtSameParkingLot() {
        // Arrange
        Attendant attendant1 = new Attendant("abc", new NearestParkingStrategy());
        Attendant attendant2 = new Attendant("def", new FarthestParkingStrategy());
        ParkingLot parkingLot = new ParkingLot(3);
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
        Attendant attendant = new Attendant("abc", new FarthestParkingStrategy());
        ParkingLot parkingLot1 = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(2);
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
    void testAttendantCheckIfCarIsParked() {
        // Arrange
        Attendant attendant = new Attendant("abc", new NearestParkingStrategy());
        ParkingLot parkingLot1 = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(2);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);
        Car car3 = new Car("AA12DD3214", Color.GREEN);

        // Act
        attendant.add(parkingLot1);
        attendant.add(parkingLot2);
        attendant.park(car1);
        attendant.park(car2);
        attendant.park(car3);
        boolean actual = attendant.isCarParked(car2);

        // Assert
        assertTrue(actual);
    }

    @Test
    void testAttendantCheckIfCarIsNotParked() {
        // Arrange
        Attendant attendant = new Attendant("abc", new NearestParkingStrategy());
        ParkingLot parkingLot1 = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(2);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);
        Car car3 = new Car("AA12DD3214", Color.GREEN);

        // Act
        attendant.add(parkingLot1);
        attendant.add(parkingLot2);
        attendant.park(car1);
        attendant.park(car3);
        boolean actual = attendant.isCarParked(car2);

        // Assert
        assertFalse(actual);
    }

    @Test
    void testAttendantUnparkingACar() {
        // Arrange
        Attendant attendant = new Attendant("abc", new FarthestParkingStrategy());
        ParkingLot parkingLot1 = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(2);
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
        Attendant attendant = new Attendant("abc", new FarthestParkingStrategy());
        ParkingLot parkingLot1 = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(2);
        Car car1 = new Car("AB12WE2345", Color.WHITE);
        Car car2 = new Car("AB12FE2345", Color.WHITE);

        // Act
        attendant.add(parkingLot1);
        attendant.add(parkingLot2);
        Ticket parkingTicket = attendant.park(car1);
        attendant.setParkingStrategy(new NearestParkingStrategy());
        Ticket actual = attendant.park(car2);

        // Assert
        assertEquals(new Ticket(1, 2), parkingTicket);
        assertEquals(new Ticket(0, 1), actual);
    }
}
