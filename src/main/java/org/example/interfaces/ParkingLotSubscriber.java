package org.example.interfaces;

import org.example.enums.ParkingLotEvent;

public interface ParkingLotSubscriber {
    default void notify(ParkingLotEvent event, ParkingLotPublisher publisher) {
        System.out.println(event.toString() + " " + publisher.toString());
    }
}
