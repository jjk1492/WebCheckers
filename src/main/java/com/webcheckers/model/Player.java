package com.webcheckers.model;

/**
 * class that represents a player
 * @author Shannon Quinn
 */
public class Player {

    /**
     * user name of this player
     */
    private String playerName;

    /**
     * creates a player
     * @param playerName name of player
     */
    public Player(String playerName){
        this.playerName = playerName;
    }

    /**
     * getter for playerName
     * @return playerName
     */
    public String getPlayerName() {
        return playerName;
    }
}
