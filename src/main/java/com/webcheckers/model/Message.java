package com.webcheckers.model;

/**
 * class used to represent messages in the application
 * essentially an interface but annoying since we have to Gson it so whatever
 * @author Zeke Miller
 */
public abstract class Message {

    public enum Type {
        info,
        error
    }

    private Type type;
    private String text;

    /**
     * protected since it's abstract and we want a pseudo-interface
     * @param type the Type of Message
     * @param text the Text of the Message
     */
    protected Message( Type type, String text ) {
        this.type = type;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Type getType() {
        return type;
    }


    @Override
    public String toString() {
        return type + " " + text;
    }
}
