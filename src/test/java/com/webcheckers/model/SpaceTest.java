package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Nick Sander
 */
@Tag("Model-Tier")
public class SpaceTest {

    @Test
    public void testNull() {
        Space space = new Space( 3 );
        assertNotNull( space, "expected non-null object" );
        assertNull( space.getPiece(), "expected null message" );
    }

}
