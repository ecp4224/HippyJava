package com.ep.hippyjava.test;

import java.util.Scanner;

import com.ep.hippyjava.bot.HippyBot;
import com.ep.hippyjava.model.Room;

public class MyTestBot extends HippyBot {

    @Override
    public void recieveMessage(String message, String from, Room room) {
        System.out.println(from + "(" + room.getTrueName() + ")" + ": " + message);
    }

    @Override
    public String username() {
        return "username";
    }

    @Override
    public String password() {
        return "password";
    }

    @Override
    public void onLoad() {
        boolean b = joinRoom("room name");
        if (b) {
            System.out.println("Joined " + getSelectedRoom().getXMPPName() + " !");
        }
        else
            System.out.println("I didnt join :(");
        new Thread() {
            
            @Override
            public void run() {
                final Scanner scan = new Scanner(System.in);
                while (true) {
                    String line = scan.nextLine();
                    sendMessage(line);
                }
            }
        }.start();
    }

    @Override
    public String nickname() {
        return "display name";
    }

    @Override
    public String apiKey() {
        return "apikey(optional)";
    }
}
