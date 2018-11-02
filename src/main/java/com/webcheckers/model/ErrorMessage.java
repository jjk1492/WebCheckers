package com.webcheckers.model;

/**
 * @author Zeke Miller
 */
public class ErrorMessage extends Message {

    public ErrorMessage( String text ) {
        super( Type.error, text );
    }
}
