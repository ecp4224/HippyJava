package com.ep.hippyjava.eventsystem;

public interface Executor {
    public void execute( Listener listen, Event event ) throws Exception;
}

