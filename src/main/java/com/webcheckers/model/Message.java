package com.webcheckers.model;

/**
 * class used to represent messages in the application
 */
public class Message {

    /**
     * enumeration to determine what type of message is being displayed
     */
    public enum Type{
        INFO,
        ERROR
    }

    private String text;
    private Type type;

    /**
     * constructor for the message
     * @param text - the actual message
     * @param type - type of message
     */
    public Message(String text, Type type) {
        this.text = text;
        this.type = type;
    }

    /**
     * getter for the text within the message
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * getter for the type of message
     * @return type
     */
    public Type getType() {
        return type;
    }
}
