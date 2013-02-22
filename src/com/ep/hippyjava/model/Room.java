package com.ep.hippyjava.model;

import static com.ep.hippyjava.utils.Constants.CONF_URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.RoomInfo;
import org.jivesoftware.smackx.muc.SubjectUpdatedListener;


public class Room {
    
    private MultiUserChat chat;
    private RoomInfo info;
    private String subject;
    private String name;
    private HipchatRoomInfo hinfo;
    
    public static Room createRoom(String name, MultiUserChat chat, XMPPConnection con) {
        final Room r = new Room(name, chat);
        try {
            r.info = MultiUserChat.getRoomInfo(con, (name.indexOf("@") != -1 ? name : name + "@" + CONF_URL));
        } catch (XMPPException e) {
            e.printStackTrace();
        }
        r.subject = r.info.getSubject();
        chat.addSubjectUpdatedListener(new SubjectUpdatedListener() {
            public void subjectUpdated(String newsubject, String from) {
                r.subject = newsubject;
            }
        });
        return r;
    }
    
    public static Room createRoom(String APIKey, String name, MultiUserChat chat, XMPPConnection con) {
        Room r = createRoom(name, chat, con);
        if (APIKey != null && !APIKey.equals(""))
            r.hinfo = HipchatRoomInfo.getInfo(APIKey, r);
        return r;
    }
    
    public static Room createRoom(String APIKey, String name) {
        Room r = new Room(name);
        r.hinfo = HipchatRoomInfo.getInfo(APIKey, r);
        r.subject = r.hinfo.getTopic();
        return r;
    }
    
    private Room(String name, MultiUserChat chat) {
        this.name = name;
        this.chat = chat;
    }
    
    private Room(String name) {
        this.name = name;
    }
    
    /**
     * Get the current amount of useres in this room.
     * If this room is not connected, then this method may return -1.
     * To test the connection of this room, then use the method {@link Room#isConnected}
     * @return
     */
    public int getUserCount() {
        if (!isConnected())
            return -1;
        return chat.getOccupantsCount();
    }
    
    /**
     * Check to see if this room is able to send messages. This method (as of 0.1) only checks the
     * connection by checking to see if the {@link MultiUserChat} object is not null.
     */
    public boolean isConnected() {
        return chat != null;
    }
    
    /**
     * Get the XMPP name of this room. This does NOT include the full XMPP_JID for this room.
     * If you would like, then use {@link Room#getXMPP_JID}
     * @return
     */
    public String getXMPPName() {
        return (name.indexOf("@") != -1 ? name.split("\\@")[0] : name);
    }
    
    /**
     * Get the full XMPP_JID for this room.
     * @return
     */
    public String getXMPP_JID() {
        return (name.indexOf("@") != -1 ? name : name + "@" + CONF_URL);
    }
    
    /**
     * Get the true name for this room. The API key is used to connect to the hipchat API to get
     * the room information for this room. However, this is used as a fall back, if the room info has already been obtained recently, then
     * this parameter wont be used. If you think that you wont need an API Key for this call, then use {@link Room#getTrueName()}
     * @param APIKey
     *              The API Key for your hipchat account to obtain information for this room.
     * @return
     */
    public String getTrueName(String APIKey) {
        if (hinfo == null) {
            hinfo = HipchatRoomInfo.getInfo(APIKey, this);
            if (hinfo == null)
                return null;
        }
        return hinfo.getRoomName();
    }
    
    /**
     * Get the true name for this room.
     * @return
     */
    public String getTrueName() {
        if (hinfo == null)
            return null;
        return hinfo.getRoomName();
    }
    
    /**
     * Get basic information from hipchat about this room.
     * @return
     */
    public HipchatRoomInfo getHipchatRoomInfo() {
        return hinfo;
    }
    
    /**
     * Get basic information from hipchat about this room. The API key is used to connect to the hipchat API to get
     * the room information for this room. However, this is used as a fall back, if the room info has already been obtained recently, then
     * this parameter wont be used. If you think that you wont need an API Key for this call, then use {@link Room#getHipchatRoomInfo()}
     * @param APIKey
     *               The API Key for your hipchat account to obtain information for this room.
     * @return
     */
    public HipchatRoomInfo getHipchatRoomInfo(String APIKey) {
        if (hinfo == null) {
            hinfo = HipchatRoomInfo.getInfo(APIKey, this);
            if (hinfo == null)
                return null;
        }
        return hinfo;
    }
    
    /**
     * Get the current subject for this room. If the subject is null or equals "", then the subject will be gotten from the active connection
     * to the room. If no active connection is present, then it will fall back to using {@link Room#getHipchatRoomInfo()} to get the topic.
     * If that is null, then a null or empty subject may be returned.
     * @return
     */
    public String getSubject() {
        if (subject == null || subject.equals("")) {
            if (info != null)
                subject = info.getSubject();
            else if (hinfo != null)
                subject = hinfo.getTopic();
        }
        return subject;
    }
    
    /**
     * Set the subject for this room. This method may only be used when an active connection to the room is present.
     * @param subject
     * @return
     *        Returns whether the change was successful or not.
     */
    public boolean setSubject(String subject) {
        if (chat == null)
            return false;
        try {
            chat.changeSubject(subject);
        } catch (XMPPException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public List<String> getConnectedUsers() {
        Iterator<String> temp = chat.getOccupants();
        List<String> copy = new ArrayList<String>();
        while (temp.hasNext())
            copy.add(temp.next());
        return Collections.unmodifiableList(copy);
    }
    
    /**
     * Send a message to this room. This method may only be used when an active connection to the room is present.
     * @param message
     *              The message to send.
     * @param from
     *            The name of the user who sent this message.
     * @return
     *        Whether the action was successful or not.
     */
    public boolean sendMessage(String message, String from) {
        if (chat == null)
            return false;
        try {
            chat.sendMessage(message);
            return true;
        } catch (XMPPException e) {
            e.printStackTrace();
        }
        return false;
    }

}
