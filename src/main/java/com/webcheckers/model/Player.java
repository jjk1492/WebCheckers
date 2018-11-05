package com.webcheckers.model;

import java.util.ArrayList;

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
    public String getName() { return name; }

    /**
     * checks if 2 players are equal
     * @param o object to check
     * @return true or flase
     */
    @Override
    public boolean equals(Object o){
        if(!(o instanceof Player)){
            return false;
        }
        Player p = (Player) o;
        return this.name.equals(p.getName());
    }
}
