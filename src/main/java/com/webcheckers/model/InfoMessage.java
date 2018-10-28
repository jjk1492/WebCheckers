package com.webcheckers.model;

/**
 * @author Zeke Miller
 */
public class InfoMessage implements Message {

    private static final String TYPE = "INFO";
    private final String message;

    public InfoMessage( String message ) {
        this.message = message;
    }

    @Override
    public String getText() {
        return message;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
