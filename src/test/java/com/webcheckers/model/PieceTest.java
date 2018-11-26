package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.webcheckers.model.Color.*;
import static com.webcheckers.model.Piece.*;
import static com.webcheckers.model.Piece.Type.KING;
import static com.webcheckers.model.Piece.Type.SINGLE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Nick Sander
 */
@Tag("Model-tier")
class PieceTest {
    /**
     * Tests to make sure a the color can be set and returned properly, with both constructors.
     */
    @Test
    void getColor() {
        Color expectedDefault = RED;
        Color expectedKing = WHITE;

        Piece defaultPiece = new SinglePiece(expectedDefault, State.OPEN);
        Piece kingPiece = new KingPiece(expectedKing, State.OPEN);

        Color actualDefault = defaultPiece.getColor();
        Color actualKing = kingPiece.getColor();

        assertEquals(expectedDefault, actualDefault, "Default Piece Color should be : " + expectedDefault);
        assertEquals(expectedKing, actualKing, "King Piece Color should be : " + expectedKing);
    }

    /**
     * Tests to make sure a the color can be set and returned properly, with both constructors.
     */
    @Test
    void getType() {
        Piece defaultPiece = new SinglePiece(RED, State.OPEN);
        Piece kingPiece = new KingPiece(WHITE, State.OPEN);

        Type actualDefault = defaultPiece.getType();
        Type actualKing = kingPiece.getType();

        assertEquals(SINGLE, actualDefault, "Default Piece Type should be : " + SINGLE);
        assertEquals(KING, actualKing, "King Piece Type should be : " + KING);
    }

    /**
     * test to check if pieces are equal or not and checks hashcode
     */
    @Test
    void equalsTest() {
        Piece singlePiece = new SinglePiece(RED, State.OPEN);
        boolean equal = singlePiece.equals(singlePiece);
        assertTrue(equal);

        Piece kingPiece = new KingPiece(WHITE, State.BLOCKED);
        boolean notEqual = kingPiece.equals(singlePiece);
        assertFalse(notEqual);

        Piece singlePieceCopy = singlePiece.copy();
        assertTrue(singlePiece.hashCode() == singlePieceCopy.hashCode());
        assertEquals(singlePiece, singlePieceCopy);

        String s = null;
        boolean diffObjects = singlePiece.equals(s);
        assertFalse(diffObjects);

    }
}
