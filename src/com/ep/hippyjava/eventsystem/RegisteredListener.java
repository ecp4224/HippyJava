package com.ep.hippyjava.eventsystem;

public class RegisteredListener {
    private Listener listen;
    private Executor executor;
    private Priority priority;
    
    public RegisteredListener( Listener listen, Executor executor, Priority priority ) {
        this.executor = executor;
        this.listen = listen;
        this.priority = priority;
    }
    
    public RegisteredListener( Listener listen, Executor executor ) {
        this(listen, executor, Priority.Normal);
    }
    
    public Listener getListen() {
        return listen;
    }
    
    public Executor getExecutor() {
        return executor;
    }
    
    public void execute( Event event ) throws Exception {
        if ( event instanceof Cancelable ) {
            if ( ( ( Cancelable )event ).isCancelled() )
                return;
        }
        executor.execute( listen, event );
    }
    
    public Priority getPriority() {
        return priority;
    }

}

