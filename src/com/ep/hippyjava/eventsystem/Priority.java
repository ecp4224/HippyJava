package com.ep.hippyjava.eventsystem;

public enum Priority {
    
    /**
     * Low is called first, so any small/not important changes
     * can be made first
     * 
     * Low priority should also be used when you want to
     * check for something first or check as early as possible
     */
    Low(0),
    
    /**
     * This is the default and should be used for normal checking
     * or normal changing
     */
    Normal(1),
    
    /**
     * This should be used when you want to change something last.
     * 
     * This priority will get the last say over what happens in the
     * event.
     */
    High(2),
    
    /**
     * This should only be used for checking. This priority is called
     * last, but it should not be used for changing.
     */
    System_Level(3);
    
    
    private int important;
    
    private Priority( int important ) {
        this.important = important;
    }
    
    public int getImportantance(){
        return important;
    }

}

