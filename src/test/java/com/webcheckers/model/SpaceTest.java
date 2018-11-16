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
        Piece piece = new Piece( Color.RED, Piece.State.OPEN);
        space.setPiece(piece);
        assertEquals(piece, space.getPiece());
    }

    @Test
    void testSpaceCopy(){
        Space space = new Space(5);
        Space copy = new Space(space);

        assertEquals(copy.getCellIdx(), 5);
        assertEquals(copy.isValid(), false);
        assertEquals(copy.getPiece(), null);
    }
}
