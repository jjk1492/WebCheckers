package com.webcheckers.model;

import com.webcheckers.ui.PostHomeRoute;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the methods of the Board class
 */
@Tag("Model-Tier")
public class BoardTest {

    @Test
    public void ConstructorTest_PlayerAssignment(){

        final Board CuT = new Board();

        Space cut00Space = CuT.getSpace(new Position(0,0));
        Space cut77Space = CuT.getSpace(new Position(7,7));

        assertEquals(0,cut00Space.getCellIdx());
        assertEquals(7,cut77Space.getCellIdx());
        assertFalse(cut00Space.isValid());
        assertFalse(cut77Space.isValid());
    }

    @Test
    public void fillRedBoardTest(){

        final Board CuT = new Board();

        CuT.fillRedBoard();
        for(int r =0; r<7; r++)
            for(int c = 0; c<7;c++)
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

        final Board CuT = new Board();

        CuT.fillWhiteBoard();
        for(int r =0; r<7; r++)
            for(int c = 0; c<7;c++)
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
    public void getHalfwayTest(){
        final Board CuT = new Board();
        Position start = new Position(0,0);
        Position end = new Position(2,2);
        assertSame(CuT.getSpace(new Position(1,1)), CuT.getHalfway(start,end));

        start = new Position(0,0);
        end = new Position(1,1);
        assertNull(CuT.getHalfway(start,end));

    }

    @Test
    public void applyMoveTest(){
        final Board CuT = new Board();
        CuT.fillRedBoard();
        Position start = new Position(5,0);
        Position end = new Position(4,1);
        Move move= new Move(start,end);
        Piece piece = CuT.getPiece(start);
        CuT.applyMove(move,piece);
        assertTrue(CuT.getPiece(end).getColor() == Color.RED);
        assertNull(CuT.getPiece(start));

    }

    @Test
    public void applyJumpTest(){
        final Board CuT = new Board();
        CuT.fillRedBoard();
        Position start = new Position(5,0);
        Position end = new Position(3,2);
        Move move= new Move(start,end);
        Piece piece = CuT.getPiece(start);
        CuT.applyMove(move,piece);
        assertTrue(CuT.getPiece(end).getColor() == Color.RED);
        assertNull(CuT.getPiece(start));

    }

}
