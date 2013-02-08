package com.ep.hippyjava.test;

import com.ep.hippyjava.bot.HippyBot;
import com.ep.hippyjava.networking.Room;

public class MyTestBot extends HippyBot {

    @Override
    public void recieveMessage(String message, String from, Room room) {
        if 
    }

    @Override
    public String username() {
        return "jid";
    }

    @Override
    public String password() {
        return "password";
    }

    @Override
    public void onLoad() {
        boolean b = joinRoom("room_id");
        if (b)
            System.out.println("Joined " + getSelectedRoom().getXMPPName() + " !");
        else
            System.out.println("I didnt join :(");
    }

    @Override
    public String nickname() {
        return "nickname";
    }

    @Override
    public String apiKey() {
        return "apiKey";
    }
}
