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

        if(name.matches("[a-zA-Z_0-9]+([a-zA-Z_0-9]|\\s)*$")){
            return true;
        }
        return false;
    }

    /**
     * test for this class
     * @param pl an instance of PlayerLobby
     */
    public void test(PlayerLobby pl){

        boolean one = pl.isValid("the kraken");
        boolean two = pl.isValid("123name");
        boolean three = pl.isValid(" ");
        boolean four = pl.isValid(" 56name");

        System.out.println("the kraken : " + one);
        System.out.println("123name : " + two);
        System.out.println(" : " + three);
        System.out.println(" 56name : " + four);

    }

    /**
     * tests
     * @param args
     */
    public static void main(String[] args) {
        PlayerLobby pl = new PlayerLobby();
        pl.test(pl);
    }
}
