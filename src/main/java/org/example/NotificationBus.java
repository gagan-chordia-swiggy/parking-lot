package org.example;

import org.example.enums.ParkingLotEvent;
import org.example.interfaces.ParkingLotPublisher;
import org.example.interfaces.ParkingLotSubscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationBus {
    private static final NotificationBus INSTANCE = new NotificationBus();
    private final Map<ParkingLotEvent, ArrayList<ParkingLotSubscriber>> subscribers;
    private NotificationBus () {
       this.subscribers = new HashMap<>();
       for (ParkingLotEvent event : ParkingLotEvent.values()) {
           subscribers.put(event, new ArrayList<>());
       }
    }

    public static NotificationBus instance() {
        return INSTANCE;
    }

    public void publish(ParkingLotPublisher publisher, ParkingLotEvent event) {
        for (ParkingLotSubscriber subscriber : subscribers.get(event)) {
            subscriber.notify(event, publisher);
        }
    }

    public void subscribe(ParkingLotSubscriber observer, ParkingLotEvent event){
        subscribers.get(event).add(observer);
    }
}
