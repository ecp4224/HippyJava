package com.ep.hippyjava.bot;

import java.util.List;

import com.ep.hippyjava.model.HipchatUser;
import com.ep.hippyjava.model.Room;
import com.ep.hippyjava.networking.Connection;

public interface Bot {
    
    /**
     * This method is called whenever a message is received
     */
    public void receiveMessage(String message, String from, Room room);
    
    /**
     * This method is called after the bot connects and logins.
     * You can use this method to autojoin a room.
     */
    public void onLoad();
    
    /**
     * Run this bot, this method should connect and login, and register any events.
     * @param con
     */
    public void run(Connection con);
    
    /**
     * Send a message to the currently selected room. You can get the currently
     * selected room by calling {@link Bot#getSelectedRoom()} and change the currently
     * selected room by calling {@link Bot#changeRoom(Room)}
     * @param message The body of the message to send
     */
    public void sendMessage(String message);
    
    /**
     * Send a message to the room provided. You can also send the message by calling
     * {@link Room#sendMessage(String, String)}
     * @param message The body of the message to send
     * @param room The room object to send this message to.
     */
    public void sendMessage(String message, Room room);
    
    /**
     * Change the currently selected room. You can get the currently selected room by
     * calling {@link Bot#getSelectedRoom()}
     * @param room The room object to change to
     */
    public void changeRoom(Room room);
    
    /**
     * Get the currently selected room
     * @return The room object that is currently selected
     */
    public Room getSelectedRoom();
    
    /**
     * Get an unmodifiable list of {@link HipchatUser}'s. These users may be offline, online, or may be deleted.
     * @return
     */
    public List<HipchatUser> getUsers();
    
    /**
     * The username this bot will login into the server with.
     * @return
     */
    public String username();
    
    /**
     * This is the nickname the bot will join rooms with.
     * @return
     */
    public String nickname();
    
    /**
     * The password this bot will use to connect.
     * @return
     */
    public String password();
    
    /**
     * Get the connection object that is handling the XMPP API for this bot.
     * @return
     */
    public Connection getConnection();
}
