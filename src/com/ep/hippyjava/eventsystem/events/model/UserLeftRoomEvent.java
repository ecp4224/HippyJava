package com.ep.hippyjava.eventsystem.events.model;

import com.ep.hippyjava.eventsystem.EventList;
import com.ep.hippyjava.eventsystem.events.UserRoomEvent;
import com.ep.hippyjava.model.HipchatUser;
import com.ep.hippyjava.model.Room;

public class UserLeftRoomEvent extends UserRoomEvent {

    private static final EventList events = new EventList();
    
    public UserLeftRoomEvent(Room room, HipchatUser user, String nick) {
        super(room, user, nick);
    }

    @Override
    public EventList getEvents() {
        return events;
    }
    
    public static EventList getEventList() {
        return events;
    }

}
