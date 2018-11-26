package com.webcheckers.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AIPlayerTest {

    @Test
    public void takeTurnTest(){
        AIPlayer CuT = new AIPlayer();
        assertEquals("ai_player", CuT.getName(), "name should be ai_player");
        Game game = mock(Game.class);
        CuT.takeTurn(game);
        verify(game).applyTurn();
    }

}
