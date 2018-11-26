package com.webcheckers.application;

import com.webcheckers.model.AIPlayer;
import com.webcheckers.model.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds the Players that exist and checks if new players can be added
 * @author Shannon Quinn
 * @author Zeke Miller
 */
public class PlayerLobby {


    // fields

    private final Map<String,Player> players;
    private AIPlayer ai_player;


    // constructors

    /**
     * Typical constructor, handles setup for the Lobby
     */
    public PlayerLobby() {
        players = new HashMap<>();
        ai_player = new AIPlayer();
    }


    // methods

    /**
     * checks if name is valid and in use already if not
     * adds the player to the map
     * @param name name the player wants to use
     * @return boolean
     */
    public synchronized boolean addPlayer( String name ) {
        if ( ! isValid( name ) ) {
            return false;
        }
        if ( players.containsKey( name ) ) {
            return false;
        }

        Player p = new Player( name );
        players.put( name, p );
        return true;
    }

    /**
     * get the number of players signed in
     * @return number of players
     */
    public int getPlayerCount() {
        return players.size();
    }

    /**
     * get a set of all players
     * @return set of players
     */
    public Collection<String> getAllPlayers() {
        return players.keySet();
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    /**
     * removes a player
     * @param name player we want to remove
     */
    public synchronized void removePlayer( String name ){
        players.remove(name);
    }

    /**
     * checks if a string contains at least one alphanumeric character
     * and doesn't start with a space
     * @param name string to check
     * @return boolean
     */
    public boolean isValid( String name ) {
        return name.matches("[a-zA-Z_0-9]+([a-zA-Z_0-9]|\\s)*$" );

    }

    /**
     * get a player object from the map
     * @param name username of player to find
     * @return player object or null
     */
    public Player getPlayer( String name ) {
        if(name.equals("ai_player"))
            return ai_player;
        return players.get( name );
    }

    /**
     * get he ai player
     * @return ai_player object
     */
    public AIPlayer getAIPlayer() {
        return ai_player;
    }

}


