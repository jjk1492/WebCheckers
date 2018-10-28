package com.webcheckers.model;

/**
 * @author Zeke Miller
 */
public class ErrorMessage implements Message {

    private static final String TYPE = "ERROR";
    private final String message;

    public ErrorMessage( String message ) {
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
