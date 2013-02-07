package com.ep.hippyjava.test;

import com.ep.hippyjava.bot.HippyBot;
import com.ep.hippyjava.networking.Room;

public class MyTestBot extends HippyBot {

    @Override
    public void recieveMessage(String message, String from, Room room) {
        System.out.println(from + ": " + message);
        if (!from.equals(nickname()))
            sendMessage("Hello!", room);
    }

    @Override
    public String username() {
        return "jabberID";
    }

    @Override
    public String password() {
        return "yourpassword";
    }

    @Override
    public void onLoad() {
        boolean b = joinRoom("room_name");
        if (b)
            System.out.println("Joined room_name !");
        else
            System.out.println("I didnt join :(");
    }

    @Override
    public String nickname() {
        return "younick";
    }
}
