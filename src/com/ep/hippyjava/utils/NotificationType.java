package com.ep.hippyjava.utils;


/**
 * Determines how the message is treated by the hipchat server and rendered inside HipChat applications.
 */
public enum NotificationType {
    
    /**
     * Message is rendered as HTML and receives no special treatment. Must be valid HTML and entities must be escaped (e.g.: &amp; instead of &). May contain basic tags: a, b, i, strong, em, br, img, pre, code. Special HipChat features such as @mentions, emoticons, and image previews are NOT supported when using this format.
     */
    HTML("html"),
    
    /**
     * Message is treated just like a message sent by a user. Can include @mentions, emoticons, pastes, and auto-detected URLs (Twitter, YouTube, images, etc).
     */
    TEXT("text");
    
    String type;
    NotificationType(String type) { this.type = type; }
    
    public String getType() { return type; }
}