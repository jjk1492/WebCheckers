package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
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

    private Player redPlayer;
    private Player whitePlayer;
    private Game CuT;
    static String validMoveMessage = "Your move was valid!";

    @BeforeEach
    public  void setup(){
        redPlayer = new Player("red");
        whitePlayer = new Player("white");
        CuT = new Game(redPlayer, whitePlayer);
    }


    /**
     * checks if the game class was set ip correctly
     */
    @Test
    public void ConstructorTest_PlayerAssignment() {

        Player CutRedPlayer = CuT.getRedPlayer();
        Player CutWhitePlayer = CuT.getWhitePlayer();

        assertEquals(redPlayer, CutRedPlayer);
        assertEquals(whitePlayer, CutWhitePlayer);
    }

    /**
     * checks that the red player is going first
     */
    @Test
    public void ConstructorTest_RedGoesFirst() {

        Color currentColor = CuT.getActiveColor();
        Player currentPlayer = CuT.getActivePlayer();

        assertEquals(Color.RED, currentColor);
        assertEquals(redPlayer, currentPlayer);
    }


    /**
     * checks if the active player swaps correctly
     */
    @Test
    public void SwapTurnTest() {

        Player playerBeforeSwap = CuT.getActivePlayer();
        Color colorBeforeSwap = CuT.getActiveColor();

        CuT.swapTurn();

        Player playerAfterSwap = CuT.getActivePlayer();
        Color colorAfterSwap = CuT.getActiveColor();

        //Check swapping from Red to White
        assertEquals(redPlayer, playerBeforeSwap);
        assertEquals(Color.RED, colorBeforeSwap);
        assertEquals(whitePlayer, playerAfterSwap);
        assertEquals(Color.WHITE, colorAfterSwap);

        CuT.swapTurn();

        //Check swapping for white to red
        assertEquals(redPlayer, CuT.getActivePlayer());
        assertEquals(Color.RED, CuT.getActiveColor());

    }

    /**
     * checks if a valid move responds correctly
     */
    @Test
    public void ValidateMoveTest_ValidMove() {

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
    public void ValidateMoveTest_OccupiedSpace() {

        //Create the positions needed to test
        Position startingPos = new Position(5, 2);
        Position endingPos = new Position(6, 3);

        Move backwardsMove = new Move(startingPos, endingPos);

        Message moveMessage = CuT.tryMove(backwardsMove);

        String invalidMoveMessage = "You cannot move to an occupied space!";
        assertEquals(Message.Type.error, moveMessage.getType());
        assertEquals(invalidMoveMessage, moveMessage.getText());
    }


    /**
     * makes sure a regular piece can't move backwards
     */
    @Test
    public void ValidateMoveTest_BackwardMove() {

        //Create the positions needed to test

        Position startingPos = new Position(5, 0);
        Position endingPos = new Position(4, 1);
        Move forwardMove = new Move(startingPos, endingPos);
        Message moveMessage = CuT.tryMove(forwardMove);
        assertEquals(Message.Type.info, moveMessage.getType());
        assertEquals(validMoveMessage, moveMessage.getText());

        CuT.applyTurn();
        CuT.swapTurn();

        Position backStartingPos = new Position(4, 1);
        Position backEndingPos = new Position(5, 0);
        Move backwardMove = new Move(backStartingPos, backEndingPos);
        Message backMoveMessage = CuT.tryMove(backwardMove);
        String invalidMoveMessage = "That piece can't do that!";
        assertEquals(Message.Type.error, backMoveMessage.getType());
        assertEquals(invalidMoveMessage, backMoveMessage.getText());
    }


    /**
     * checks if 2 games are equal or not
     */
    @Test
    public void EqualsTest() {

        final Player redPlayer2 = new Player("red2");
        final Player whitePlayer2 = new Player("white2");

        final Game CuT2 = new Game(redPlayer2, whitePlayer2);

        final String s = "not a board";

        boolean sameGame = CuT.equals(CuT);
        boolean nullGame = CuT.equals(null);
        boolean differentGame = CuT.equals(CuT2);
        boolean diffObjects = CuT.equals(s);

        assertTrue(sameGame);
        assertFalse(nullGame);
        assertFalse(differentGame);
        assertFalse(diffObjects);
    }


    /**
     * checks if backup move sends the correct boolean
     */
    @Test
    public void backupMoveTest() {

        boolean noMoveToBackup = CuT.backupMove();

        Position startingPos = new Position(5, 2);
        Position validEndingPos = new Position(4, 3);
        Move move = new Move(startingPos, validEndingPos);
        Message message = CuT.tryMove(move);
        assertEquals(message.getType(), Message.Type.info);
        assertEquals(message.getText(), validMoveMessage);

        boolean backupToMake = CuT.backupMove();
        boolean noMoveToBackup2 = CuT.backupMove();

        assertFalse(noMoveToBackup);
        assertTrue(backupToMake);
        assertFalse(noMoveToBackup2);
    }

    /**
     * checks if moves get applied for white and red player
     */
    @Test
    public void applyTurn() {

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

        Move firstMoveWhite = new Move(startingPos, validEndingPosLeft);
        Move secondMoveWhite = new Move(startingPos, validEndingPosRight);
        Message message = CuT.tryMove(firstMoveWhite);
        Message message1 = CuT.tryMove(secondMoveWhite);
        assertEquals(message.getText(), validMoveMessage);
        assertEquals(message1.getText(), validMoveMessage);
        CuT.applyTurn();

    }

    /**
     * misc tests to get code coverage up
     */
    @Test
    public void miscTests(){
        int redPieces = CuT.getRedPiecesRemaining();
        int whitePieces = CuT.getWhitePiecesRemaining();
        assertEquals(12, redPieces);
        assertEquals(12, whitePieces);

        Board redBoard = CuT.getRedBoard();
        assertNotNull(redBoard, "The red board should not be null! ");
        Board whiteBoard = CuT.getWhiteBoard();
        assertNotNull(whiteBoard, "The white board should not be null! ");
    }

}
