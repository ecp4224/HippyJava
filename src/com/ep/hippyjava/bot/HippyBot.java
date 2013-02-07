package com.ep.hippyjava.bot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPException;

import com.ep.hippyjava.HippyJava;
import com.ep.hippyjava.eventsystem.EventHandler;
import com.ep.hippyjava.eventsystem.Listener;
import com.ep.hippyjava.eventsystem.events.MessageRecivedEvent;
import com.ep.hippyjava.networking.Connection;
import com.ep.hippyjava.networking.Room;

public abstract class HippyBot implements Bot, Listener {
    
    protected Connection con;
    private Room selected;
    
    @Override
    public void run(Connection con) {
        HippyJava.events.registerEvents(new Listener() {
           @SuppressWarnings("unused")
           @EventHandler
           public void messageEvent(MessageRecivedEvent event) {
               recieveMessage(event.body(), event.from(), event.getRoom());
           }
        });
        this.con = con;
        try {
            con.connect();
            con.login(username(), password());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        onLoad();
    }
    
    @Override
    public void sendMessage(String message) {
        if (selected == null)
            return;
        sendMessage(message, selected);
    }
    
    public void changeRoom(String name) {
        if (findRoom(name) != null)
            changeRoom(findRoom(name));
    }
    
    public boolean joinRoom(String name) {
        try {
            con.joinRoom(name, nickname());
            selected = findRoom(name);
            return true;
        } catch (XMPPException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    @Override
    public Room getSelectedRoom() {
        return selected;
    }
    
    @Override
    public void changeRoom(Room room) {
        this.selected = room;
    }
    
    public void sendMessageToRoom(String name, String message) {
        if (findRoom(name) != null)
            sendMessage(message, findRoom(name));
    }
    
    @Override
    public void sendMessage(String message, Room room) {
        room.sendMessage(message, nickname());
    }

    @Override
    public List<String> users() {
        final Roster r = con.getRoster();
        ArrayList<String> users = new ArrayList<String>();
        for (RosterEntry e : r.getEntries())
            users.add(e.getName());
        return Collections.unmodifiableList(users);
    }
    
    public Room findRoom(String name) {
        return con.findConnectedRoom(name);
    }
    
    public List<Room> getRooms() {
        return con.getRooms();
    }

    @Override
    public Connection getConnection() {
        return con;
    }
}
