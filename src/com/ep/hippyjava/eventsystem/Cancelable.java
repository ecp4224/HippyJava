package com.ep.hippyjava.eventsystem;

public interface Cancelable {
    
    /**
     * Check to see if the event is canceled.
     * @return Weather or not the event is canceled
     */
    public boolean isCancelled();
    
    /**
     * Cancel the event.
     * This should be used when you want to stop the server from
     * doing the default action it would normally do.
     * @param cancel If set to true, the event will cancel. If set to false, the event will not cancel
     */
    public void setCancel( boolean cancel );

}

