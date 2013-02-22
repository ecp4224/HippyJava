package com.ep.hippyjava.eventsystem.events;

import com.ep.hippyjava.model.HipchatUser;
import com.ep.hippyjava.model.Room;

public abstract class UserRoomEvent extends RoomEvent {

    private HipchatUser user;
    private String nick;
    
    public UserRoomEvent(Room room, HipchatUser user, String nick) {
        super(room);
        this.nick = nick;
        this.user = user;
    }
    
    /**
     * Get the hipchat user that left the room.
     * If no API Key is present in the bot, then this method may return null. The only time it will not return null is if
     * the room was created with an API Key
     * @return
     */
    public HipchatUser getHipchatUser() {
        return user;
    }
    
    /**
     * Return the nickname of this user.
     * <b>Example</b>
     * "Bob Joe"
     * @return
     */
    public String getNickname() {
        return nick.split("\\/")[1];
    }
    
    /**
     * Return the JID of this user
     * <b>Example:</b>
     * "11111_111111@chat.hipchat.com"
     * @return
     */
    public String getJID() {
        return nick;
    }

}
