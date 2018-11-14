package com.webcheckers.application;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * application tier for game creator
 *
 * @author Spencer Fleming
 */
public class GameCenter {

    private static final Logger LOG = Logger
            .getLogger( GameCenter.class.getName() );

    private Map<String,Game> playersInGame;
    private Map<String,String> gameWinners; //<player, winner> of previous games
    private PlayerLobby playerLobby;

    public GameCenter( PlayerLobby playerLobby ) {
        playersInGame = new HashMap<>();
        gameWinners = new HashMap<>();
        this.playerLobby = playerLobby;
    }
    

    /**
     * makes a game
     * adds the game to the map at the redPlayer name and whitePlayer name
     * @param redPlayer name of the red player
     * @param whitePlayer name of the white player
     * @return boolean
     */
    public synchronized boolean addGame( String redPlayer, String whitePlayer ) {

        Player red = playerLobby.getPlayer(redPlayer);
        Player white = playerLobby.getPlayer(whitePlayer);

        if ( red == null || white == null || redPlayer.equals( whitePlayer ) ) {
            return false;
        }
        if ( playersInGame.containsKey( redPlayer) || playersInGame.containsKey( whitePlayer )) {
            return false;
        }

        Game game = new Game(red, white);
        playersInGame.put(redPlayer, game);
        playersInGame.put(whitePlayer, game);

        LOG.finer( String.format( "Made a game for %s and %s", redPlayer,
                                  whitePlayer ) );

        return true;
    }


    /**
     * gets a given player's current opponent
     * @param name the player to find the opponent for
     * @return the opponent's name
     */
    public synchronized String getOpponent(String name) {
        Game game = getGame( name );
        if ( game != null ) {
            Player red = game.getRedPlayer();
            if ( red != null && !red.getName().equals( name ) ) {
                return red.getName();
            }
            Player white = game.getWhitePlayer();
            if ( white != null && !white.getName().equals( name ) ) {
                return white.getName();
            }
        }

        return null;
    }

    /**
     * returns whether or not the player in question is currently the active player.
     * @param name name of the player that needs to know whether or not it is their turn
     * @return a boolean, true if the player is active and false otherwise
     */
    public boolean isPlayerActive( String name ) {
        Game game = getGame( name );
        if ( name != null && game != null ) {
            return name.equals( game.getActivePlayer().getName() );
        }
        return false;
    }

    /**
     * used to fully apply a player's turn when prepared to do so
     * @param name the player applying their turn
     */
    public void finishTurn( String name ) {
        Game game = getGame( name );
        if ( game != null ) {
            game.applyTurn();
        }
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
     * @return the winner of the game or null if the game has not been won
     * @param name of a player in the game
     */
    public synchronized String gameWinner(String name){
        if (!isPlayerInGame(name)){
            return gameWinners.get(name);
        }
        String winner = getGame(name).gameWinner();
        if (winner != null){
            finishedGame(getOpponent(winner));
        }
        return winner;
    }

    /**
     * removes players from playersInGame
     * and adds them to gameWinners
     * @param player1 player we want to remove
     */
    public synchronized void finishedGame( String player1) {
        String opponent = getOpponent(player1);
        gameWinners.put(player1, opponent);
        gameWinners.put(opponent,opponent);
        if(isPlayerInGame(player1)){
            playersInGame.remove(player1);
            if(isPlayerInGame(opponent)) {
                playersInGame.remove(opponent);
            }
        }
    }

    /**
     * also removes players from playersInGame, but allows removing one player at a time
     * @param name player to be removed
     */
    public synchronized void removePlayer(String name){
        if (isPlayerInGame(name)){
            finishedGame(name);
            playerLobby.removePlayer(name);
        }
    }
}
