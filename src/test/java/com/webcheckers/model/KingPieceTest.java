package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.webcheckers.model.Color.RED;
import static com.webcheckers.model.Color.WHITE;
import static com.webcheckers.model.Piece.State.BLOCKED;
import static com.webcheckers.model.Piece.State.JUMP;
import static com.webcheckers.model.Piece.State.OPEN;
import static com.webcheckers.model.Piece.Type.KING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the KingPiece
 */
@Tag("Model-Tier")
public class KingPieceTest {

    /**
     * Tests the normal, complete constructor
     */
    @Test
    public void completeConstructorTest() {
        Piece kingPiece = new KingPiece( RED, JUMP );
        assertEquals( RED, kingPiece.getColor() );
        assertEquals( JUMP, kingPiece.getState() );
        assertEquals( KING, kingPiece.getType() );
    }


    /**
     * Tests the constructor with default state
     */
    @Test
    public void halfConstructorTest() {
        Piece kingPiece = new KingPiece( RED );
        assertEquals( RED, kingPiece.getColor() );
        assertEquals( OPEN, kingPiece.getState() );
        assertEquals( KING, kingPiece.getType() );
    }


    /**
     * Tests the copy constructor with both a single and king to copy from
     */
    @Test
    public void copyConstructorTest() {
        Piece kingPiece = new KingPiece( WHITE, JUMP );
        Piece kingCopy = new KingPiece( kingPiece );
        assertEquals( WHITE, kingCopy.getColor() );
        assertEquals( JUMP, kingCopy.getState() );
        assertEquals( KING, kingCopy.getType() );
        Piece singlePiece = new SinglePiece( WHITE, BLOCKED );
        Piece singleCopy = new KingPiece( singlePiece );
        assertEquals( WHITE, singleCopy.getColor() );
        assertEquals( BLOCKED, singleCopy.getState() );
        assertEquals( KING, singleCopy.getType() );
    }


    /**
     * Tests the copy method on a KingPiece
     */
    @Test
    public void copyTest() {
        Piece kingPiece = new KingPiece( RED, JUMP );
        Piece copy = kingPiece.copy();
        assertEquals( RED, copy.getColor() );
        assertEquals( JUMP, copy.getState() );
        assertEquals( KING, copy.getType() );
    }


    /**
     * Tests some valid steps in the isValidStep method
     */
    @Test
    public void validStepsTest() {
        Position start = new Position( 4, 3 );
        Position end1 = new Position( 3, 4 );
        Position end2 = new Position( 3, 2 );
        Position end3 = new Position( 5, 2 );
        Position end4 = new Position( 5, 4 );
        Move valid1 = new Move( start, end1 );
        Move valid2 = new Move( start, end2 );
        Move valid3 = new Move( start, end3 );
        Move valid4 = new Move( start, end4 );
        Move valid1Reverse = new Move( end1, start );
        Move valid2Reverse = new Move( end2, start );
        Move valid3Reverse = new Move( end3, start );
        Move valid4Reverse = new Move( end4, start );
        Piece king = new KingPiece( WHITE );
        assertTrue( king.isValidStep( valid1 ) );
        assertTrue( king.isValidStep( valid2 ) );
        assertTrue( king.isValidStep( valid3 ) );
        assertTrue( king.isValidStep( valid4 ) );
        assertTrue( king.isValidStep( valid1Reverse ) );
        assertTrue( king.isValidStep( valid2Reverse ) );
        assertTrue( king.isValidStep( valid3Reverse ) );
        assertTrue( king.isValidStep( valid4Reverse ) );
    }


    /**
     * Tests some invalid steps in the isValidStep method
     */
    @Test
    public void invalidStepsTest() {
        Position start = new Position( 4, 3 );
        Position end1 = new Position( 0, 3 );
        Position end2 = new Position( 2, 5 );
        Position end3 = new Position( 6, 1 );
        Move invalid1 = new Move( start, end1 );
        Move invalid2 = new Move( start, end2 );
        Move invalid3 = new Move( start, end3 );
        Move invalid4 = new Move( start, start );
        Move invalid1Reverse = new Move( end1, start );
        Move invalid2Reverse = new Move( end2, start );
        Move invalid3Reverse = new Move( end3, start );
        Piece king = new KingPiece( WHITE );
        assertFalse( king.isValidStep( invalid1 ) );
        assertFalse( king.isValidStep( invalid2 ) );
        assertFalse( king.isValidStep( invalid3 ) );
        assertFalse( king.isValidStep( invalid4 ) );
        assertFalse( king.isValidStep( invalid1Reverse ) );
        assertFalse( king.isValidStep( invalid2Reverse ) );
        assertFalse( king.isValidStep( invalid3Reverse ) );
    }


    /**
     * Tests some valid jumps in the isValidJump method
     */
    @Test
    public void validJumpsTest() {
        Position start = new Position( 4, 3 );
        Position end1 = new Position( 2, 5 );
        Position end2 = new Position( 2, 1 );
        Position end3 = new Position( 6, 1 );
        Position end4 = new Position( 6, 5 );
        Move valid1 = new Move( start, end1 );
        Move valid2 = new Move( start, end2 );
        Move valid3 = new Move( start, end3 );
        Move valid4 = new Move( start, end4 );
        Move valid1Reverse = new Move( end1, start );
        Move valid2Reverse = new Move( end2, start );
        Move valid3Reverse = new Move( end3, start );
        Move valid4Reverse = new Move( end4, start );
        Piece king = new KingPiece( WHITE );
        assertTrue( king.isValidJump( valid1 ) );
        assertTrue( king.isValidJump( valid2 ) );
        assertTrue( king.isValidJump( valid3 ) );
        assertTrue( king.isValidJump( valid4 ) );
        assertTrue( king.isValidJump( valid1Reverse ) );
        assertTrue( king.isValidJump( valid2Reverse ) );
        assertTrue( king.isValidJump( valid3Reverse ) );
        assertTrue( king.isValidJump( valid4Reverse ) );
    }


    /**
     * tests some invalid jumps in the isValidJump method
     */
    @Test
    public void invalidJumpsTest() {
        Position start = new Position( 4, 3 );
        Position end1 = new Position( 3, 4 );
        Position end2 = new Position( 7, 0 );
        Position end3 = new Position( 2, 3 );
        Move invalid1 = new Move( start, end1 );
        Move invalid2 = new Move( start, end2 );
        Move invalid3 = new Move( start, end3 );
        Move invalid4 = new Move( start, start );
        Move invalid1Reverse = new Move( end1, start );
        Move invalid2Reverse = new Move( end2, start );
        Move invalid3Reverse = new Move( end3, start );
        Piece king = new KingPiece( WHITE );
        assertFalse( king.isValidJump( invalid1 ) );
        assertFalse( king.isValidJump( invalid2 ) );
        assertFalse( king.isValidJump( invalid3 ) );
        assertFalse( king.isValidJump( invalid4 ) );
        assertFalse( king.isValidJump( invalid1Reverse ) );
        assertFalse( king.isValidJump( invalid2Reverse ) );
        assertFalse( king.isValidJump( invalid3Reverse ) );
    }
}