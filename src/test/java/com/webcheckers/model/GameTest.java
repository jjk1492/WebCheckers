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
}
