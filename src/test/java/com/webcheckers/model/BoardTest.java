package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.webcheckers.model.Color.RED;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the methods of the Board class
 */
@Tag("Model-Tier")
public class BoardTest {
    private Board CuT;

    @BeforeEach
    public void setup(){
        CuT = new Board();
    }

    /**
     * checks correct spaces
     */
    @Test
    public void ConstructorTest_PlayerAssignment(){


        Space cut00Space = CuT.getSpace(new Position(0,0));
        Space cut77Space = CuT.getSpace(new Position(7,7));

        assertEquals(0,cut00Space.getCellIdx());
        assertEquals(7,cut77Space.getCellIdx());
        assertFalse(cut00Space.isValid());
        assertFalse(cut77Space.isValid());
        assertNull(CuT.getSpace(new Position(9,9)));
    }


    /**
     * test to fill white board
     */
    @Test
    public void fillWhiteBoardTest(){
        CuT = CuT.flipped();
        for(int r =0; r<8; r++)
            for(int c = 0; c<8;c++)
                if(r > 4 || r < 3) {
                    assertFalse(CuT.spaceIsValid(r,c));
                    if((r + c) % 2 == 1){
                        if (r > 4){
                            assertSame(CuT.getPiece(new Position(r, c)).getColor(), Color.WHITE);
                        } else assertSame(CuT.getPiece(new Position(r, c)).getColor(), RED);
                    }
                    else {
                        assertNull(CuT.getPiece(new Position(r, c)));
                    }

                }else if((r + c) % 2 == 0) assertFalse(CuT.spaceIsValid(r,c));
    }

    /**
     * board copy test
     */
    @Test
    public void copyConstructorTest(){
        Board copyCuT = new Board(CuT);

        for(int r =0; r<8; r++)
            for(int c = 0; c<8;c++)
                if(r > 4 || r < 3) {
                    assertFalse(copyCuT.spaceIsValid(r,c));
                    if((r + c) % 2 == 1){
                        if (r > 4){
                            assertSame(copyCuT.getPiece(new Position(r, c)).getColor(), RED);
                        }else assertSame(CuT.getPiece(new Position(r, c)).getColor(), Color.WHITE);
                    }
                    else {
                        assertNull(copyCuT.getPiece(new Position(r, c)));
                    }

                }else if((r + c) % 2 == 0) assertFalse(copyCuT.spaceIsValid(r,c));

        Position start = new Position(5,0);
        Position end = new Position(4,1);
        Move move = new Move(start,end);
        copyCuT.applyMove(move);
        assertNull(CuT.getSpace(end).getPiece());
    }

    /**
     * check halfway point
     */
    @Test
    public void getHalfwayTest(){
        Position start = new Position(0,0);
        Position end = new Position(2,2);
        assertSame(CuT.getSpace(new Position(1,1)), CuT.getHalfway(start,end));

        start = new Position(0,0);
        end = new Position(1,1);
        assertNull(CuT.getHalfway(start,end));

    }

    /**
     *
     */
    @Test
    public void validateMoveTest(){
        Position start = new Position(5,0);
        Position end = new Position(4,1);
        Move move = new Move(start,end);
        Message message = CuT.validateMove(move);
        assertSame("info",message.getType().toString());

        start = new Position(5,1);
        end = new Position(4,0);
        move = new Move(start,end);
        message = CuT.validateMove(move);
        assertSame("error",message.getType().toString());

        start = new Position(5,0);
        end = new Position(5,2);
        move = new Move(start,end);
        message = CuT.validateMove(move);
        assertSame("error",message.getType().toString());

        start = new Position(5,0);
        end = new Position(4,0);
        move = new Move(start,end);
        message = CuT.validateMove(move);
        assertSame("error",message.getType().toString());

        start = new Position(5,0);
        end = new Position(3,2);
        move = new Move(start,end);
        message = CuT.validateMove(move);
        assertSame("error",message.getType().toString());
    }

    @Test
    public void applyMoveTest(){
        Position start = new Position(5,0);
        Position end = new Position(4,1);
        Move move= new Move(start,end);
        CuT.applyMove(move);
        assertTrue(CuT.getPiece(end).getColor() == RED);
        Piece p = CuT.getPiece(start);
        assertNull(p);

    }


    @Test
    public void applyJumpTest(){

        Position whiteOccupy = new Position(4, 1);
        Space space = CuT.getSpace(whiteOccupy);
        Piece whitePiece = new SinglePiece(Color.WHITE, Piece.State.OPEN);
        space.setPiece(whitePiece);
        space.setValid(false);

        Position start = new Position(5,0);
        Position end = new Position(3,2);
        Move move= new Move(start,end);
        Piece piece = CuT.getPiece(start);
        CuT.applyMove(move);
        assertTrue(CuT.getPiece(end).getColor() == RED);
        assertNull(CuT.getPiece(start));
    }

    @Test
    public void jumpOverOwnPieceRed(){
       Position redStart = new Position(6,1);
       Position redEnd = new Position(4,3);
       Move moveRed = new Move(redStart,redEnd);


       Message message = CuT.validateMove(moveRed);
       String ownPieceMessage = "You can't jump over your own piece!";
       assertEquals(message.getType(), Message.Type.error);
       assertEquals(message.getText(), ownPieceMessage);

    }

    @Test
    public void jumpOverOwnPieceWhite(){
        Position whiteStart = new Position(6,5);
        Position whiteEnd = new Position(4,7);
        Move moveWhite = new Move(whiteStart,whiteEnd);

        Message message = CuT.validateMove(moveWhite);
        String ownPieceMessage = "You can't jump over your own piece!";
        assertEquals(message.getType(), Message.Type.error);
        assertEquals(message.getText(), ownPieceMessage);

    }

    @Test
    public void validJump(){
        Position whiteOccupy = new Position(4, 1);
        Space space = CuT.getSpace(whiteOccupy);
        Piece whitePiece = new SinglePiece(Color.WHITE, Piece.State.OPEN);
        space.setPiece(whitePiece);
        space.setValid(false);

        Position start = new Position(5,0);
        Position end = new Position(3,2);
        Move move = new Move(start,end);
        Message message = CuT.validateMove(move);
        String validJump = "Valid jump!";
        assertEquals(message.getType(), Message.Type.info);
        assertEquals(message.getText(), validJump);

    }

    @Test
    void jumpOverOwnPieceTesr(){

    }

    @Test
    public void iteratorTest(){
        assertNotNull(CuT.iterator());
    }


    private void kingSetup() {
        /*
        5,0 -> 4,1
        2,5 -> 3,4
        4,1 -> 3,0
        1,6 -> 2,5
        6,1 -> 5,0
        0,7 -> 1,6
        5,4 -> 4,3
        2,5 -> 3,6
        4,3 -> 2,5 -> 0,7
        */
        Position redStart1 = new Position(5, 0);
        Position redEnd1 = new Position(4, 1);
        Move red1 = new Move(redStart1, redEnd1);
        Position whiteStart1 = new Position(2, 5);
        Position whiteEnd1 = new Position(3, 4);
        Move white1 = new Move(whiteStart1, whiteEnd1);
        Position redStart2 = new Position(4, 1);
        Position redEnd2 = new Position(3, 0);
        Move red2 = new Move(redStart2, redEnd2);
        Position whiteStart2 = new Position(1, 6);
        Position whiteEnd2 = whiteStart1;
        Move white2 = new Move(whiteStart2, whiteEnd2);
        Position redStart3 = new Position(6, 1);
        Position redEnd3 = redStart1;
        Move red3 = new Move(redStart3, redEnd3);
        Position whiteStart3 = new Position(0, 7);
        Position whiteEnd3 = whiteStart2;
        Move white3 = new Move(whiteStart3, whiteEnd3);
        Position redStart4 = new Position(5, 4);
        Position redEnd4 = new Position(4, 3);
        Move red4 = new Move(redStart4, redEnd4);
        Position whiteStart4 = whiteStart1;
        Position whiteEnd4 = new Position( 3, 6 );
        Move white4 = new Move( whiteStart4, whiteEnd4 );
        Position redStart5 = redEnd4;
        Position redMid5 = new Position( 2, 5 );
        Position redEnd5 = new Position( 0,7 );
        Move red51 = new Move( redStart5, redMid5 );
        Move red52 = new Move( redMid5, redEnd5 );

        CuT.applyMove( red1 );
        CuT.endTurn();
        assertEquals( Piece.Type.SINGLE, CuT.getPiece( redEnd1 ).getType() );
        assertEquals( Color.RED, CuT.getPiece( redEnd1 ).getColor() );
        CuT.applyMove( white1 );
        CuT.endTurn();
        assertEquals( Piece.Type.SINGLE, CuT.getPiece( whiteEnd1 ).getType() );
        assertEquals( Color.WHITE, CuT.getPiece( whiteEnd1 ).getColor() );
        CuT.applyMove( red2 );
        CuT.endTurn();
        assertEquals( Piece.Type.SINGLE, CuT.getPiece( redEnd2 ).getType() );
        assertEquals( Color.RED, CuT.getPiece( redEnd2 ).getColor() );
        CuT.applyMove( white2 );
        CuT.endTurn();
        assertEquals( Piece.Type.SINGLE, CuT.getPiece( whiteEnd2 ).getType() );
        assertEquals( Color.WHITE, CuT.getPiece( whiteEnd2 ).getColor() );
        CuT.applyMove( red3 );
        CuT.endTurn();
        assertEquals( Piece.Type.SINGLE, CuT.getPiece( redEnd3 ).getType() );
        assertEquals( Color.RED, CuT.getPiece( redEnd3 ).getColor() );
        CuT.applyMove( white3 );
        CuT.endTurn();
        assertEquals( Piece.Type.SINGLE, CuT.getPiece( whiteEnd3 ).getType() );
        assertEquals( Color.WHITE, CuT.getPiece( whiteEnd3 ).getColor() );
        CuT.applyMove( red4 ) ;
        CuT.endTurn();
        assertEquals( Piece.Type.SINGLE, CuT.getPiece( redEnd4 ).getType() );
        assertEquals( Color.RED, CuT.getPiece( redEnd4 ).getColor() );
        CuT.applyMove( white4 );
        CuT.endTurn();
        assertEquals( Piece.Type.SINGLE, CuT.getPiece( whiteEnd4 ).getType() );
        assertEquals( Color.WHITE, CuT.getPiece( whiteEnd4 ).getColor() );
        CuT.applyMove( red51 );
        CuT.applyMove( red52 );
        CuT.endTurn();
    }

    /**
     * tests to make sure pieces are crowned at the correct time
     */
    @Test
    public void crowningTest() {
        Position endPosition = new Position( 0, 7 );
        assertEquals( Piece.Type.SINGLE, CuT.getPiece( endPosition ).getType() );
        assertEquals( Color.WHITE, CuT.getPiece( endPosition ).getColor() );
        kingSetup();
        assertEquals( Piece.Type.KING, CuT.getPiece( endPosition ).getType() );
        assertEquals( Color.RED, CuT.getPiece( endPosition ).getColor() );
    }


    /**
     * tests to make sure pieces are crowned at the correct time
     */
    @Test
    public void backwardsMoveTest() {
        kingSetup();
        Position endPosition = new Position( 0, 7 );
        assertEquals( Piece.Type.KING, CuT.getPiece( endPosition ).getType() );
        assertEquals( Color.RED, CuT.getPiece( endPosition ).getColor() );
        Position newEnd = new Position( 1, 6 );
        Move backwardsGood = new Move( endPosition, newEnd );
        assertTrue( CuT.getPiece( endPosition ).isValidStep( backwardsGood ) );
        Move whiteMove = new Move( new Position( 3, 6 ), new Position( 4, 7 ) );
        CuT.applyMove( whiteMove );
        CuT.endTurn();
        CuT.applyMove( backwardsGood );
        assertEquals( Piece.Type.KING, CuT.getPiece( newEnd ).getType() );
        assertEquals( Color.RED, CuT.getPiece( newEnd ).getColor() );
    }
}