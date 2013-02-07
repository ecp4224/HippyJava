package com.ep.hippyjava;

import com.ep.hippyjava.bot.Bot;
import com.ep.hippyjava.eventsystem.EventSystem;
import com.ep.hippyjava.networking.Connection;

public class HippyJava {
    
    public static final EventSystem events = new EventSystem();
    
    public void run(Bot bot) {
        Connection con = new Connection();
        bot.run(con);
        try {
            con.waitForEnd();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void runBot(Bot bot) {
        new HippyJava().run(bot);
    }
}
