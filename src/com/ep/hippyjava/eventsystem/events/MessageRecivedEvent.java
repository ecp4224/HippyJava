package com.ep.hippyjava.eventsystem.events;

import org.jivesoftware.smack.packet.Message;

import com.ep.hippyjava.eventsystem.EventList;
import com.ep.hippyjava.model.Room;

public class MessageRecivedEvent extends RoomEvent {

    
    private static final EventList events = new EventList();
    private Message message;
    
    public MessageRecivedEvent(Room room, Message message) {
        super(room);
        this.message = message;
    }
    
    /**
     * Returns who the packet is being sent "from" or null if the value is not set. The XMPP protocol often makes the "from" attribute optional, so it does not always need to be set.
     * 
     * @see Message#getFrom()
     * @return
     */
    public String from() {
        return message.getFrom();
    }
    
    /**
     * Returns the default body of the message. If the body of the message is null, then this
     * method will return "".
     * The body is the main message contents. 
     * @see Message#getBody()
     * @return
     */
    public String body() {
        return message.getBody() == null ? "" : message.getBody();
    }
    
    /**
     * Get the message object that holds the XMPP users for this message.
     * @return
     */
    public Message getMessage() {
        return message;
    }

    @Override
    public EventList getEvents() {
        return events;
    }
    
    public static EventList getEventList() {
        return events;
    }

}
