package com.webcheckers.application;

import com.webcheckers.model.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds the Players that exist and checks if new players can be added
 * Singleton object so all Players are managed in one instance
 * @author Shannon Quinn
 */
public class PlayerLobby {

    // singleton management

    private static PlayerLobby INSTANCE = null;

    public static PlayerLobby getInstance() {
        if ( INSTANCE == null ) {
            INSTANCE = new PlayerLobby();
        }
        return INSTANCE;
    }


    // fields

    private Map<String,Player> players;


    // constructors

    /**
     * Typical constructor, handles setup for the Lobby
     */
    private PlayerLobby() {
        players = new HashMap<>();
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
    public Player getPlayer( String name ) { return players.get( name );}

}


