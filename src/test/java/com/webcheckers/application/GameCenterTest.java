package com.webcheckers.application;

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

    private GameCenter gameCenter = GameCenter.getInstance();
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
        PlayerLobby.getInstance().addPlayer(redName);
        PlayerLobby.getInstance().addPlayer(whiteName);
        gameCenter.addGame(redName, whiteName);

        boolean redPlayer = gameCenter.isPlayerInGame(redName);
        assertTrue( redPlayer, redName + " should be in a game." );

        boolean whitePlayer = gameCenter.isPlayerInGame(whiteName);
        assertTrue( whitePlayer, whiteName + " should be in a game." );

        gameCenter.finishedGame(redName, whiteName);

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
        PlayerLobby.getInstance().addPlayer(redName);
        PlayerLobby.getInstance().addPlayer(whiteName);
        PlayerLobby.getInstance().addPlayer(tempName);
        gameCenter.addGame(whiteName, tempName);
        gameCenter.addGame(redName, whiteName);

        boolean redPlayer = gameCenter.isPlayerInGame(redName);
        assertFalse( redPlayer, redName + " should not be in a game." );

        gameCenter.finishedGame(whiteName, "test");
    }

}
