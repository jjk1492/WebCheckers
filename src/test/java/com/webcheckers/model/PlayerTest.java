package com.webcheckers.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    /**
     * tests if name gets set & returned correctly
     */
    @Test
    void getName() {
        String expected = "The Kraken";
        Player p = new Player( expected );
        String actual = p.getName();
        assertEquals( expected, actual, "Name should be " + expected );
    }

    /**
     * checks if 2 players are equal
     */
    @Test
    void equalsTest(){
        Player p1 = new Player("The Kraken");
        Player p2 = new Player("Buttercup");
        String s = "not a player object";

        boolean notEqual = p1.equals(p2);

        boolean diffObject = p1.equals(s);

        assertFalse(notEqual);
        assertFalse(diffObject);
    }
}