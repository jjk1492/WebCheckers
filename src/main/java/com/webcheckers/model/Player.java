package com.webcheckers.model;

/**
 * class that represents a player
 * @author Shannon Quinn
 */
public class Player {

    /**
     * user name of this player
     */
    private String name;

    /**
     * creates a player
     * @param name name of player
     */
    public Player( String name ) {
        this.name = name;
    }

    /**
     * getter for name
     * @return name
     */
    public String getName() {
        return name;
    }
}
