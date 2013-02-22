package com.ep.hippyjava.eventsystem.events;

import com.ep.hippyjava.eventsystem.Event;
import com.ep.hippyjava.model.Room;

public abstract class RoomEvent extends Event {
    
    private Room room;
    
    public RoomEvent(Room room) {
        this.room = room;
    }
    
    /**
     * Get the room that this event is associated with.
     * @return
     */
    public Room getRoom() {
        return room;
    }

}
