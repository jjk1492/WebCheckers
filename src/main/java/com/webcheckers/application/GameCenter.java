package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * application tier for game creator
 *
 * @author Spencer Fleming
 */
public class GameCenter {
    private static GameCenter INSTANCE = null;
    private static PlayerLobby playerLobby;

    public static GameCenter getInstance() {
        if ( INSTANCE == null ) {
            INSTANCE = new GameCenter();
        }
        return INSTANCE;
    }

    private Map<String,Game> playersInGame;

    private GameCenter() {
        playersInGame = new HashMap<>();
    }

    /**
     * makes a game
     * adds the game to the map at the redPlayer name and whitePlayer name
     * @param redPlayer name of the red player
     * @param whitePlayer name of the white player
     * @return boolean
     */
    public synchronized boolean addGame( String redPlayer, String whitePlayer ) {

        playerLobby = PlayerLobby.getInstance();
        Player red = playerLobby.getPlayer(redPlayer);
        Player white = playerLobby.getPlayer(whitePlayer);

        if ( red == null || white == null ) {
            return false;
        }
        if ( playersInGame.containsKey( redPlayer) || playersInGame.containsKey( whitePlayer )) {
            return false;
        }

        Game game = new Game(red, white);
        playersInGame.put(redPlayer, game);
        playersInGame.put(whitePlayer, game);

        return true;
    }

    /**
     * returns a game currently playing
     * @param name one of the players in the game
     */
    public synchronized Game getGame(String name){
        return playersInGame.get(name);
    }

    /**
     * returns whether or not the player is in a game
     * @param name of the player
     */
    public synchronized boolean isPlayerInGame(String name){
        return playersInGame.containsKey(name);
    }

    /**
     * removes players from playersInGame
     * @param player1 player we want to remove
     * @param player2 player we want to remove
     */
    public synchronized void finishedGame( String player1, String player2 ) {
        playersInGame.remove(player1);
        playersInGame.remove(player2);
    }
}
