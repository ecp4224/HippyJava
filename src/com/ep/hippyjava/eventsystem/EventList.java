package com.ep.hippyjava.eventsystem;

import java.util.*;
import java.util.Map.Entry;
public class EventList {

    private volatile RegisteredListener[] events = null;

    private EnumMap< Priority, ArrayList< RegisteredListener > > muffinbag;

    private static ArrayList<EventList> mail = new ArrayList<EventList>();


    public EventList() {
        muffinbag = new EnumMap< Priority, ArrayList< RegisteredListener > >(Priority.class);
        for (Priority o : Priority.values()) {
            muffinbag.put( o, new ArrayList< RegisteredListener >() );
        }
        synchronized( mail ) {
            mail.add( this );
        }
    }

    public synchronized void register( RegisteredListener listener ) {
        if ( muffinbag.get( listener.getPriority() ).contains( listener ) )
            throw new IllegalStateException( "This listener is already registered!" );
        events = null;
        muffinbag.get( listener.getPriority() ).add( listener );
    }

    /**
     * Register a collection of new listeners in this handler list
     *
     * @param listeners listeners to register
     */
    public void registerAll( Collection< RegisteredListener > listeners ) {
        for ( RegisteredListener listener : listeners ) {
            register( listener );
        }
    }

    public RegisteredListener[] getRegisteredListeners() {
        RegisteredListener[] handlers;
        while ( ( handlers = this.events ) == null ) bake(); // This prevents fringe cases of returning null
        return handlers;
    }


    public synchronized void bake() {
        if ( events != null ) return; // don't re-bake when still valid
        List< RegisteredListener > entries = new ArrayList< RegisteredListener >();
        for ( Entry< Priority, ArrayList< RegisteredListener > > entry : muffinbag.entrySet() ) {
            entries.addAll( entry.getValue() );
        }
        events = entries.toArray( new RegisteredListener[ entries.size() ] );
    }
    
    /**
    * Remove a specific listener from this handler
    *
    * @param listener listener to remove
    */
    public synchronized void unregister( Listener listener ) {
        boolean changed = false;
        for ( List< RegisteredListener > list : muffinbag.values() ) {
            for ( ListIterator< RegisteredListener > i = list.listIterator(); i.hasNext(); ) {
                if ( i.next().getListen().equals( listener ) ) {
                    i.remove();
                    changed = true;
                }
            }
        }
        if ( changed ) events = null;
    }

}

