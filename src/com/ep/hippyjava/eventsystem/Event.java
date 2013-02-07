package com.ep.hippyjava.eventsystem;
public abstract class Event {
    
    private String name;
    
    public Event() { }
    
    /**
     * Get a list of registered listeners
     * @return The list of listeners
     */
    public abstract EventList getEvents();
    
    /**
     * Get the name of the event
     * @return The name
     */
    public String getEventName() {
        return ( name == null || name.equals( "" ) ) ? getClass().getSimpleName() : name;
    }
}

