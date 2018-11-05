package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Nick Sander
 */
@Tag("Model-Tier")
public class SpaceTest {

    private Space space;

    @Test
    public void testNull() {
        space = new Space( 7 );
        assertNotNull( space, "expected non-null object" );
        assertNull( space.getPiece(), "expected null message" );
    }

    @Test
    public void testValid() {
        space = new Space( 3 );
        assertFalse(space.isValid(), "expected false (non-valid space).");
        space.setValid(true);
        assertTrue(space.isValid(), "expected true (valid space).");
    }

    @Test
    public void testPiece() {
        space = new Space(0);
        Piece piece = new Piece(Color.RED, Piece.Type.SINGLE);
        space.setPiece(piece);
        assertEquals(piece, space.getPiece());
    }
}
