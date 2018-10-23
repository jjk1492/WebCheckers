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
        Player p = new Player(expected);
        String actual = p.getName();
        assertEquals(expected, actual, "Name should be " + expected);
    }
}