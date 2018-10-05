package com.webcheckers.application;

import com.webcheckers.model.Player;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Shannon Quinn
 */
public class PlayerLobby {

    private static PlayerLobby INSTANCE = null;

    public static PlayerLobby getInstance() {
        if ( INSTANCE == null ) {
            INSTANCE = new PlayerLobby();
        }
        return INSTANCE;
    }

    Set<Player> players;

    private PlayerLobby(){
        players = new HashSet<>();
    }

    public boolean addPlayer(String name){

        return false;
    }

    public boolean isValid(String name){

        if(name.matches("[a-zA-Z_0-9]+$")){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        PlayerLobby pl = new PlayerLobby();
        boolean one = pl.isValid("this name");
        boolean two = pl.isValid("123name");
        boolean three = pl.isValid("");

        System.out.println("this name " + one);
        System.out.println("123name " + two);
        System.out.println("" + three);

    }
}
