package com.ep.hippyjava.model;

import static com.ep.hippyjava.utils.Constants.GSON;

import java.util.Date;

import com.ep.hippyjava.utils.WebUtils;

public class HipchatRoomInfo {
    private int room_id;
    private String name;
    private String topic;
    private long last_active;
    private long created;
    private int owner_user_id;
    private boolean is_archived;
    private boolean is_private;
    private String xmpp_jid;
    private HipchatRoomInfo() { }
    
    public static HipchatRoomInfo getInfo(String APIKey, Room room) {
        if (APIKey.equals(""))
            return null;
        HipchatRoomInfo[] data = getRooms(APIKey);
        if (data == null)
            return null;
        for (HipchatRoomInfo h : data) {
            if (h.equals(room))
                return h;
        }
        return null;
    }
    
    public static HipchatRoomInfo[] getRooms(String APIKey) {
        return getRoomHolder(APIKey).rooms;
    }
    
    private static RoomHolder getRoomHolder(String APIKey) {
        try {
            String JSON = WebUtils.getTextAsString("https://api.hipchat.com/v1/rooms/list?auth_token=" + APIKey);
            RoomHolder data = GSON.fromJson(JSON, RoomHolder.class);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            RoomHolder r = new RoomHolder();
            r.rooms = new HipchatRoomInfo[0];
            return r;
        }
    }
    
    public String getRoomName() {
        return name;
    }
    
    public String getTopic() {
        return topic;
    }
    
    public int getID() {
        return room_id;
    }
    
    public String getJID() {
        return xmpp_jid;
    }
    
    public boolean isPrivate() {
        return is_private;
    }
    
    public boolean isArchived() {
        return is_archived;
    }
    
    public Date getLastActive() {
        return new Date(last_active);
    }
    
    public Date getCreationDate() {
        return new Date(created);
    }
    
    public int getOwnerID() {
        return owner_user_id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Room) {
            Room r = (Room)obj;
            if (r.getXMPP_JID().equals(xmpp_jid))
                return true;
            else if (r.getXMPPName().equals(name)) //In some cases, the XMPPName turns out to be the true name :/
                return true;
            else
                return false;
        }
        if (obj instanceof HipchatRoomInfo) {
            HipchatRoomInfo r = (HipchatRoomInfo)obj;
            return r.name.equals(name) && r.is_private == is_private && r.created == created && r.xmpp_jid.equals(xmpp_jid) && r.last_active == last_active && r.is_archived == is_archived && r.owner_user_id == owner_user_id && r.room_id == room_id && r.topic.equals(topic);
        }
        return false;
    }
    
    private static class RoomHolder {
        HipchatRoomInfo[] rooms;
        public RoomHolder() { }
    }
}
