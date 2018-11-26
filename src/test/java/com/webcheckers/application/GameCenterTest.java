package com.webcheckers.application;

import com.webcheckers.model.Color;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the  GameCenter component.
 *
 * @author Spencer Fleming
 */
@Tag("Application-tier")
public class GameCenterTest {

    private PlayerLobby playerLobby = new PlayerLobby();
    private GameCenter gameCenter = new GameCenter( playerLobby );
    private String redName = "redName";
    private String whiteName = "whiteName";
    private String tempName = "tempName";

    /**
     * test to make sure gameCenter instance is not null
     */
    @Test
    void instanceNotNull() {
        assertNotNull( gameCenter,"Instance is null" );
    }

    /**
     * test to make sure you can make a game with two players who are not currently in the game
     * then successfully end the game
     */
    @Test
    void addAndFinishGame() {
        playerLobby.addPlayer(redName);
        playerLobby.addPlayer(whiteName);
        gameCenter.addGame(redName, whiteName);

        boolean redPlayer = gameCenter.isPlayerInGame(redName);
        assertTrue( redPlayer, redName + " should be in a game." );

        boolean whitePlayer = gameCenter.isPlayerInGame(whiteName);
        assertTrue( whitePlayer, whiteName + " should be in a game." );

        gameCenter.finishedGame(redName);

        redPlayer = gameCenter.isPlayerInGame(redName);
        assertFalse( redPlayer, redName + " should not be in a game." );

        whitePlayer = gameCenter.isPlayerInGame(whiteName);
        assertFalse( whitePlayer, whiteName + " should not be in a game." );
    }

    /**
     * test to make sure you cannot make a game with a player who is already in a game
     */
    @Test
    void addInvalidGame() {
        playerLobby.addPlayer(redName);
        playerLobby.addPlayer(whiteName);
        playerLobby.addPlayer(tempName);
        gameCenter.addGame(whiteName, tempName);
        gameCenter.addGame(redName, whiteName);

        boolean redPlayer = gameCenter.isPlayerInGame(redName);
        assertFalse( redPlayer, redName + " should not be in a game." );

        gameCenter.finishedGame(whiteName);
    }

    @Test
    void activePlayerTest(){
        playerLobby.addPlayer(redName);
        playerLobby.addPlayer(whiteName);
        gameCenter.addGame(redName, whiteName);
        assertTrue(gameCenter.isPlayerActive(redName));
        assertFalse(gameCenter.isPlayerActive("name"));

    }

    @Test
    void opponentCheck(){
        playerLobby.addPlayer(redName);
        playerLobby.addPlayer(whiteName);
        gameCenter.addGame(redName, whiteName);

        assertEquals(gameCenter.getOpponent(whiteName), redName);

    }

    @Test
    void removePlayer(){
        playerLobby.addPlayer(redName);
        playerLobby.addPlayer(whiteName);
        gameCenter.addGame(redName, whiteName);
        gameCenter.removePlayer(redName);
        assertNull(gameCenter.getOpponent(redName));
    }


    @Test
    void gameWinner(){
        playerLobby.addPlayer(redName);
        playerLobby.addPlayer(whiteName);
        gameCenter.addGame(redName, whiteName);
        assertNull(gameCenter.gameWinner(redName));
        assertNull(gameCenter.gameWinner(redName));
        gameCenter.finishedGame(redName);
        assertEquals(whiteName, gameCenter.gameWinner(redName));
        assertEquals(whiteName, gameCenter.gameWinner(whiteName));
    }

    @Test
    void aiMovesTest(){
        playerLobby.addPlayer(redName);
        gameCenter.addGame(redName, "ai_player");
        gameCenter.makeAIMoves(redName);
        assertTrue(gameCenter.isPlayerActive(redName));
    }

}