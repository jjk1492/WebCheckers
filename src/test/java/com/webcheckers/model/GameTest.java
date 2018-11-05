package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the methods of the Game class
 * @author John Knecht V (jjk1492@rit.edu)
 */
@Tag("Model-Tier")
public class GameTest {

    /**
     * checks if the game class was set ip correctly
     */
    @Test
    public void ConstructorTest_PlayerAssignment(){
        final Player redPlayer = new Player("red");
        final Player whitePlayer = new Player("white");
        final Game CuT = new Game(redPlayer, whitePlayer);

        Player CutRedPlayer = CuT.getRedPlayer();
        Player CutWhitePlayer = CuT.getWhitePlayer();

        assertEquals(redPlayer, CutRedPlayer);
        assertEquals(whitePlayer, CutWhitePlayer);
    }

    /**
     * checks that the red player is going first
     */
    @Test
    public void ConstructorTest_RedGoesFirst(){
        final Player redPlayer = new Player("red");
        final Player whitePlayer = new Player("white");
        final Game CuT = new Game(redPlayer, whitePlayer);

        Color currentColor = CuT.getActiveColor();
        Player currentPlayer = CuT.getActivePlayer();

        assertEquals(Color.RED, currentColor);
        assertEquals(redPlayer, currentPlayer);
    }


    /**
     * checks if the active player swaps correctly
     */
    @Test
    public void SwapTurnTest(){
        final Player redPlayer = new Player("red");
        final Player whitePlayer = new Player("white");
        final Game CuT = new Game(redPlayer, whitePlayer);

        Player playerBeforeSwap = CuT.getActivePlayer();
        Color colorBeforeSwap = CuT.getActiveColor();

        CuT.swapTurn();

        Player playerAfterSwap = CuT.getActivePlayer();
        Color colorAfterSwap = CuT.getActiveColor();

        //Check swapping from Red to White
        assertEquals( redPlayer, playerBeforeSwap );
        assertEquals( Color.RED, colorBeforeSwap );
        assertEquals( whitePlayer, playerAfterSwap );
        assertEquals( Color.WHITE, colorAfterSwap );

        CuT.swapTurn();

        //Check swapping for white to red
        assertEquals( redPlayer, CuT.getActivePlayer());
        assertEquals( Color.RED, CuT.getActiveColor());

    }

    /**
     * checks if a valid move responds correctly
     */
    @Test
    public void ValidateMoveTest_ValidMove(){
        final Player redPlayer = new Player("red");
        final Player whitePlayer = new Player("white");
        final Game CuT = new Game(redPlayer, whitePlayer);

        //Create the positions needed to test
        Position startingPos = new Position(5, 2);
        Position validEndingPosRight = new Position(4, 3);
        Position validEndingPosLeft = new Position(4, 1);

        Move firstMove = new Move(startingPos, validEndingPosLeft);
        Move secondMove = new Move(startingPos, validEndingPosRight);

        Message firstMoveMessage = CuT.tryMove(firstMove);
        Message secondMoveMessage = CuT.tryMove(secondMove);

        String validMoveMessage = "Your move was valid!";

        assertEquals(firstMoveMessage.getType(), Message.Type.info);
        assertEquals(firstMoveMessage.getText(), validMoveMessage);

        assertEquals(secondMoveMessage.getType(), Message.Type.info);
        assertEquals(secondMoveMessage.getText(), validMoveMessage);
    }

    /**
     * makes sure a move to an occupied space can't happen
     */
    @Test
    public void ValidateMoveTest_OccupiedSpace(){
        final Player redPlayer = new Player("red");
        final Player whitePlayer = new Player("white");
        final Game CuT = new Game(redPlayer, whitePlayer);

        //Create the positions needed to test
        Position startingPos = new Position(5, 2);
        Position endingPos = new Position(6, 3);

        Move backwardsMove = new Move( startingPos, endingPos);

        Message moveMessage = CuT.tryMove(backwardsMove);

        String invalidMoveMessage = "You cannot move to an occupied space!";
        assertEquals(Message.Type.error, moveMessage.getType());
        assertEquals(invalidMoveMessage, moveMessage.getText());
    }


    /**
     * makes sure a regular piece can't move backwards
     */
    @Test
    public void ValidateMoveTest_BackwardMove(){
        final Player redPlayer = new Player("red");
        final Player whitePlayer = new Player("white");
        final Game CuT = new Game(redPlayer, whitePlayer);

        //Create the positions needed to test

        Position startingPos = new Position(5, 0);
        Position endingPos = new Position(4, 1);
        Move forwardMove = new Move( startingPos, endingPos);
        Message moveMessage = CuT.tryMove(forwardMove);
        String  validMoveMessage = "Your move was valid!";
        assertEquals(Message.Type.info, moveMessage.getType());
        assertEquals(validMoveMessage, moveMessage.getText());

        CuT.applyTurn();
        CuT.swapTurn();

        Position backStartingPos = new Position(4, 1);
        Position backEndingPos = new Position(5, 0);
        Move backwardMove = new Move( backStartingPos, backEndingPos);
        Message backMoveMessage = CuT.tryMove(backwardMove);
        String  invalidMoveMessage = "That piece can't do that!";
        assertEquals(Message.Type.error, backMoveMessage.getType());
        assertEquals(invalidMoveMessage, backMoveMessage.getText());
    }


    /**
     * checks if 2 games are equal or not
     */
    @Test
    public void EqualsTest(){
        final Player redPlayer1 = new Player( "red1");
        final Player whitePlayer1 = new Player( "white1");
        final Player redPlayer2 = new Player("red2");
        final Player whitePlayer2 = new Player("white2");

        final Game CuT1 = new Game( redPlayer1, whitePlayer1 );
        final Game CuT2 = new Game( redPlayer2, whitePlayer2 );

        boolean sameGame = CuT1.equals( CuT1 );
        boolean nullGame = CuT1.equals( null );
        boolean differentGame = CuT1.equals( CuT2 );

        assertTrue( sameGame );
        assertFalse( nullGame );
        assertFalse( differentGame );
    }


    /**
     * checks if backup move sends the correct boolean
     */
    @Test
    public void backupMoveTest(){
        final Player redPlayer = new Player("red");
        final Player whitePlayer = new Player("white");
        final Game CuT = new Game(redPlayer, whitePlayer);

        boolean noMoveToBackup = CuT.backupMove();

        Position startingPos = new Position(5, 2);
        Position validEndingPos= new Position(4, 3);
        Move move = new Move(startingPos, validEndingPos);
        Message message = CuT.tryMove(move);
        String validMoveMessage = "Your move was valid!";
        assertEquals(message.getType(), Message.Type.info);
        assertEquals(message.getText(), validMoveMessage);

        boolean backupToMake = CuT.backupMove();
        boolean noMoveToBackup2 = CuT.backupMove();

        assertFalse(noMoveToBackup);
        assertTrue(backupToMake);
        assertFalse(noMoveToBackup2);
    }

    @Test
    public void applyTurn(){
        final Player redPlayer = new Player("red");
        final Player whitePlayer = new Player("white");
        final Game CuT = new Game(redPlayer, whitePlayer);

        Position startingPos = new Position(5, 2);
        Position validEndingPosRight = new Position(4, 3);
        Position validEndingPosLeft = new Position(4, 1);

        Move firstMove = new Move(startingPos, validEndingPosLeft);
        Move secondMove = new Move(startingPos, validEndingPosRight);

        CuT.tryMove(firstMove);
        CuT.tryMove(secondMove);

        Color active = CuT.getActiveColor();
        assertEquals(active, Color.RED);

        CuT.applyTurn();

        Color active2 = CuT.getActiveColor();
        assertEquals(active2, Color.WHITE);

    }

}
