package com.ep.hippyjava.networking;

import static com.ep.hippyjava.utils.Constants.CONF_URL;
import static com.ep.hippyjava.utils.Constants.XMPP_URL;
import static com.ep.hippyjava.utils.Constants.PORT;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.ep.hippyjava.networking.exceptions.LoginException;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Body;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;

import com.ep.hippyjava.HippyJava;
import com.ep.hippyjava.eventsystem.events.model.MessageRecivedEvent;
import com.ep.hippyjava.model.Room;

public final class Connection implements MessageListener, ConnectionListener {
    
    private static final ConnectionConfiguration CONNECTION_CONFIG = new ConnectionConfiguration(XMPP_URL, PORT);
    private final XMPPConnection XMPP = new XMPPConnection(CONNECTION_CONFIG);
    private boolean connected;
    private String password;
    private HashMap<Room, MultiUserChat> rooms = new HashMap<Room, MultiUserChat>();
    private HashMap<String, Chat> cache = new HashMap<String, Chat>();
    
    public void connect() throws XMPPException {
        if (connected)
            return;
        XMPP.connect();
        XMPP.addConnectionListener(this);
        connected = true;
    }
    
    public void login(String username, String password) throws LoginException {
        if (!connected)
            return;
        if (!username.contains("hipchat.com"))
            System.err.println("[HippyJava] The username being used does not look like a Jabber ID. Are you sure this is the correct username?");
        try {
            XMPP.login(username, password);
        } catch (XMPPException exception) {
            throw new LoginException("There was an error logging in! Are you using the correct username/password?", exception);
        }
        this.password = password;
    }
    
    public void sendPM(String message, String to) throws XMPPException {
        Chat c;
        if (cache.containsKey(to))
            c = cache.get(to);
        else {
            c = XMPP.getChatManager().createChat(to, this);
            cache.put(to, c);
        }
        c.sendMessage(message);
    }
    
    public void joinRoom(String room, String nickname) throws XMPPException {
        joinRoom("", room, nickname);
    }
    
    public void joinRoom(String APIKey, String room, String nickname) throws XMPPException {
        if (!connected || nickname.equals("") || password.equals("") || rooms.containsKey(room))
            return;
        MultiUserChat muc2;
        if (!isJID(room)) {
            Room temp = Room.createRoom(APIKey, room);
            room = temp.getHipchatRoomInfo(APIKey).getJID();
            muc2 = new MultiUserChat(XMPP, room);
            temp = null;
        }
        else
            muc2 = new MultiUserChat(XMPP, (room.indexOf("@") != -1 ? room : room + "@" + CONF_URL));
        muc2.join(nickname, password);
        final Room obj = Room.createRoom(APIKey, room, muc2, XMPP);
        muc2.addMessageListener(new PacketListener() {

            @Override
            public void processPacket(Packet paramPacket) {
                Message m = new Message();
                m.setBody(toMessage(paramPacket));
                m.setFrom(paramPacket.getFrom().split("\\/")[1]);
                MessageRecivedEvent event = new MessageRecivedEvent(obj, m);
                HippyJava.events.callEvent(event);
            } 
        });
        rooms.put(obj, muc2);
    }
    
    public List<Room> getRooms() {
        ArrayList<Room> roomlist = new ArrayList<Room>();
        for (Room room : rooms.keySet()) {
            roomlist.add(room);
        }
        return Collections.unmodifiableList(roomlist);
    }
    
    public boolean sendMessageToRoom(String room, String message, String nickname) {
        if (!rooms.containsKey(room)) {
            try {
                joinRoom(room, nickname);
            } catch (XMPPException e) {
                e.printStackTrace();
                return false;
            }
        }
        Room obj;
        if ((obj = findConnectedRoom(room)) != null)
            return obj.sendMessage(message, nickname);
        return false;
    }
    
    public Room findConnectedRoom(String name) {
        for (Room r : getRooms()) {
            if (r.getXMPPName().equals(name))
                return r;
        }
        return null;
    }
    
    public boolean isConnected() {
        return connected;
    }
    
    public void disconnect() {
        if (!connected)
            return;
        XMPP.disconnect();
        connected = false;
    }
    
    public Roster getRoster() {
        return XMPP.getRoster();
    }
    
    @Override
    public void processMessage(Chat arg0, Message arg1) {
        MessageRecivedEvent event = new MessageRecivedEvent(null, arg1);
        HippyJava.events.callEvent(event);
    }

    @Override
    public void connectionClosed() {
        connected = false;
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        connected = false;
    }

    @Override
    public void reconnectingIn(int seconds) {
        
    }

    @Override
    public void reconnectionFailed(Exception e) {
        if (connected)
            connected = false;
    }

    @Override
    public void reconnectionSuccessful() {
        if (!connected)
            connected = true;
    }
    
    public synchronized void waitForEnd() throws InterruptedException {
        while (true) {
            if (!connected)
                break;
            super.wait(0L);
        }
    }
    
    private boolean isJID(String name) {
        try {
            Integer.parseInt(name.split("\\_")[0]);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private String toMessage(Packet packet) {
        try {
            Field f = packet.getClass().getDeclaredField("bodies");
            f.setAccessible(true);
            @SuppressWarnings("rawtypes")
            HashSet h = (HashSet)f.get(packet);
            if (h.size() == 0)
                return "";
            for (Object obj : h) {
                if (obj instanceof Body)
                    return ((Body)obj).getMessage();
            }
            return "";
        } catch (Exception e) {
            return "";
        }
        
    }
}
