package com.ep.hippyjava.networking;

import static com.ep.hippyjava.utils.Constants.CONF_URL;

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
    public Room(String name, MultiUserChat chat, XMPPConnection con) {
        this.chat = chat;
        try {
            this.info = MultiUserChat.getRoomInfo(con, name + "@" + CONF_URL);
        } catch (XMPPException e) {
            e.printStackTrace();
        }
        this.name = name;
        this.subject = info.getSubject();
        chat.addSubjectUpdatedListener(new SubjectUpdatedListener() {
            public void subjectUpdated(String newsubject, String from) {
                subject = newsubject;
            }
        });
        
    }
    
    public int getUserCount() {
        return info.getOccupantsCount();
    }
    
    public String getName() {
        return name;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public boolean setSubject(String subject) {
        try {
            chat.changeSubject(subject);
        } catch (XMPPException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean sendMessage(String message, String from) {
        try {
            chat.sendMessage(message);
            return true;
        } catch (XMPPException e) {
            e.printStackTrace();
        }
        return false;
    }

}
