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
    void getColor(){
        Color expectedDefault = RED;
        Color expectedKing = WHITE;

        Piece defaultPiece = new Piece(expectedDefault);
        Piece kingPiece = new Piece(expectedKing, KING);

        Color actualDefault = defaultPiece.getColor();
        Color actualKing = kingPiece.getColor();

        assertEquals(expectedDefault, actualDefault, "Default Piece Color should be : " + expectedDefault);
        assertEquals(expectedKing, actualKing, "King Piece Color should be : " + expectedKing);
    }

    /**
     * Tests to make sure a the color can be set and returned properly, with both constructors.
     */
    @Test
    void getType(){
        Type expectSingle = SINGLE;
        Type expectKing = KING;

        Piece defaultPiece = new Piece(RED);
        Piece kingPiece = new Piece(WHITE, expectKing);

        Type actualDefault = defaultPiece.getType();
        Type actualKing = kingPiece.getType();

        assertEquals(expectSingle, actualDefault, "Default Piece Type should be : " + expectSingle);
        assertEquals(expectKing, actualKing, "King Piece Type should be : " + expectKing);
    }

}
