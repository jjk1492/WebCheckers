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
}
