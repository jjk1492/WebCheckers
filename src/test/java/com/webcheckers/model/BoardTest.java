package com.webcheckers.model;

import com.webcheckers.ui.PostHomeRoute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


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

    @Test
    public void fillRedBoardTest(){

        CuT.fillBoard(Color.RED);
        for(int r =0; r<8; r++)
            for(int c = 0; c<8;c++)
                if(r > 4 || r < 3) {
                    assertFalse(CuT.spaceIsValid(r,c));
                    if((r + c) % 2 == 1){
                        if (r > 4){
                            assertSame(CuT.getPiece(new Position(r, c)).getColor(), Color.RED);
                        }else assertSame(CuT.getPiece(new Position(r, c)).getColor(), Color.WHITE);
                    }
                    else {
                        assertNull(CuT.getPiece(new Position(r, c)));
                    }

                }else if((r + c) % 2 == 0) assertFalse(CuT.spaceIsValid(r,c));
    }

    @Test
    public void fillWhiteBoardTest(){

        CuT.fillBoard(Color.WHITE);
        for(int r =0; r<8; r++)
            for(int c = 0; c<8;c++)
                if(r > 4 || r < 3) {
                    assertFalse(CuT.spaceIsValid(r,c));
                    if((r + c) % 2 == 1){
                        if (r > 4){
                            assertSame(CuT.getPiece(new Position(r, c)).getColor(), Color.WHITE);
                        }else assertSame(CuT.getPiece(new Position(r, c)).getColor(), Color.RED);
                    }
                    else {
                        assertNull(CuT.getPiece(new Position(r, c)));
                    }

                }else if((r + c) % 2 == 0) assertFalse(CuT.spaceIsValid(r,c));
    }

    @Test
    public void copyConstructorTest(){
        CuT.fillBoard(Color.RED);
        Board copyCuT = new Board(CuT);

        for(int r =0; r<8; r++)
            for(int c = 0; c<8;c++)
                if(r > 4 || r < 3) {
                    assertFalse(copyCuT.spaceIsValid(r,c));
                    if((r + c) % 2 == 1){
                        if (r > 4){
                            assertSame(copyCuT.getPiece(new Position(r, c)).getColor(), Color.RED);
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

    @Test
    public void getHalfwayTest(){
        Position start = new Position(0,0);
        Position end = new Position(2,2);
        assertSame(CuT.getSpace(new Position(1,1)), CuT.getHalfway(start,end));

        start = new Position(0,0);
        end = new Position(1,1);
        assertNull(CuT.getHalfway(start,end));

    }

    @Test
    public void validateMoveTest(){
        CuT.fillBoard(Color.RED);
        Position start = new Position(5,0);
        Position end = new Position(4,1);
        Move move = new Move(start,end);
        Message message = CuT.validateMove(move, Color.RED);
        assertSame("info",message.getType().toString());

        start = new Position(5,1);
        end = new Position(4,0);
        move = new Move(start,end);
        message = CuT.validateMove(move, Color.RED);
        assertSame("error",message.getType().toString());

        start = new Position(5,0);
        end = new Position(5,2);
        move = new Move(start,end);
        message = CuT.validateMove(move, Color.RED);
        assertSame("error",message.getType().toString());

        start = new Position(5,0);
        end = new Position(4,0);
        move = new Move(start,end);
        message = CuT.validateMove(move, Color.RED);
        assertSame("error",message.getType().toString());

        start = new Position(5,0);
        end = new Position(3,2);
        move = new Move(start,end);
        message = CuT.validateMove(move, Color.RED);
        assertSame("error",message.getType().toString());
    }

    @Test
    public void applyMoveTest(){
        CuT.fillBoard(Color.RED);
        Position start = new Position(5,0);
        Position end = new Position(4,1);
        Move move= new Move(start,end);
        Piece piece = CuT.getPiece(start);
        CuT.applyMove(move);
        assertTrue(CuT.getPiece(end).getColor() == Color.RED);
        Piece p = CuT.getPiece(start);
        assertNull(p);

    }

    @Test
    public void applyJumpTest(){
        CuT.fillBoard(Color.RED);

        Position whiteOccupy = new Position(4, 1);
        Space space = CuT.getSpace(whiteOccupy);
        Piece whitePiece = new Piece(Color.WHITE, Piece.State.OPEN);
        space.setPiece(whitePiece);
        space.setValid(false);

        Position start = new Position(5,0);
        Position end = new Position(3,2);
        Move move= new Move(start,end);
        Piece piece = CuT.getPiece(start);
        CuT.applyMove(move);
        assertTrue(CuT.getPiece(end).getColor() == Color.RED);
        assertNull(CuT.getPiece(start));
    }

    @Test
    public void jumpOverOwnPieceRed(){
       CuT.fillBoard(Color.RED);
       Color activeRed = Color.RED;
       Position redStart = new Position(6,1);
       Position redEnd = new Position(4,3);
       Move moveRed = new Move(redStart,redEnd);


       Message message = CuT.validateMove(moveRed, activeRed);
       String ownPieceMessage = "You can't jump over your own piece!";
       assertEquals(message.getType(), Message.Type.error);
       assertEquals(message.getText(), ownPieceMessage);

    }

    @Test
    public void jumpOverOwnPieceWhite(){
        CuT.fillBoard(Color.WHITE);
        Color activeWhite = Color.WHITE;
        Position whiteStart = new Position(6,5);
        Position whiteEnd = new Position(4,7);
        Move moveWhite = new Move(whiteStart,whiteEnd);

        Message message = CuT.validateMove(moveWhite, activeWhite);
        String ownPieceMessage = "You can't jump over your own piece!";
        assertEquals(message.getType(), Message.Type.error);
        assertEquals(message.getText(), ownPieceMessage);

    }

    @Test
    public void validJump(){
        CuT.fillBoard(Color.RED);

        Position whiteOccupy = new Position(4, 1);
        Space space = CuT.getSpace(whiteOccupy);
        Piece whitePiece = new Piece(Color.WHITE, Piece.State.OPEN);
        space.setPiece(whitePiece);
        space.setValid(false);

        Position start = new Position(5,0);
        Position end = new Position(3,2);
        Move move = new Move(start,end);
        Message message = CuT.validateMove(move, Color.RED);
        String validJump = "Valid jump!";
        assertEquals(message.getType(), Message.Type.info);
        assertEquals(message.getText(), validJump);


    }

    @Test
    public void iteratorTest(){
        assertNotNull(CuT.iterator());
    }

}
