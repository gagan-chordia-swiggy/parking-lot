package org.example.interfaces;

import org.example.NotificationBus;
import org.example.enums.ParkingLotEvent;

public interface ParkingLotPublisher {
    default void notifyFull() {
        NotificationBus.instance().publish(this, ParkingLotEvent.FULL);
    }

    default void notifyVacant() {
        NotificationBus.instance().publish(this, ParkingLotEvent.EMPTY);
    }
}
