package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Zeke Miller
 */
@Tag("Model-Tier")
public class ErrorMessageTest {

    @Test
    public void testNull() {
        Message message = new ErrorMessage( null );
        assertNotNull( message, "expected non-null object" );
        assertNull( message.getText(), "expected null message" );
        assertEquals( Message.Type.ERROR, message.getType(), "expected type to be INFO" );
    }

    @Test
    public void testString() {
        String test = "testing";
        Message message = new ErrorMessage( test );
        assertNotNull( message, "expected non-null object" );
        assertEquals( test, message.getText(), "expected message: " + test );
        assertEquals( Message.Type.ERROR, message.getType(), "expected type to be INFO" );
    }
}
