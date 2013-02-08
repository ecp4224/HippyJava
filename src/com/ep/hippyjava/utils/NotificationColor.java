package com.ep.hippyjava.utils;

/**
 * A enum of background colors for Hipchat Notifications.
 */
public enum NotificationColor {

    YELLOW("yellow"),

    GREEN("green"),
    
    RED("red"),
    
    PURPLE("purple"),
    
    GRAY("gray"),
    
    RANDOM("random");

    String type;
    NotificationColor(String type) { this.type = type; }

    public String getType() { return type; }
}
