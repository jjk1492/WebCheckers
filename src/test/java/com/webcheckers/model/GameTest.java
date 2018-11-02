package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the methods of the Game class
 * @author John Kencht V (jjk1492@rit.edu)
 */
@Tag("Model-Tier")
public class GameTest {

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

        Message firstMoveMessage = CuT.validateMove(firstMove);
        Message secondMoveMessage = CuT.validateMove(secondMove);

        String validMoveMessage = "Your move was valid.";

        assertEquals(firstMoveMessage.getType(), Message.Type.INFO);
        assertEquals(firstMoveMessage.getText(), validMoveMessage);

        assertEquals(secondMoveMessage.getType(), Message.Type.INFO);
        assertEquals(secondMoveMessage.getText(), validMoveMessage);


    }

    @Test
    public void EqualsTest(){
        final Player redPlayer1 = new Player( "red1");
        final Player whitePlayer1 = new Player( "white1");
        final Player redPlayer2 = new Player("red2");
        final Player whitePlayer2 = new Player("white2");

        final Game CuT1 = new Game( redPlayer1, whitePlayer1);
        final Game CuT2 = new Game(redPlayer2, whitePlayer2);

        boolean sameGame = CuT1.equals(CuT1);
        boolean nullGame = CuT1.equals(null);
        boolean differentGame = CuT1.equals(CuT2);

        assertEquals(sameGame, true);
        assertEquals(nullGame, false);
        assertEquals(differentGame, false);
    }
}
