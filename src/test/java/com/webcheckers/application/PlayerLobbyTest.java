package com.webcheckers.application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerLobbyTest {

    private PlayerLobby pl = PlayerLobby.getInstance();

    /**
     * test to make sure pl is not null
     */
    @Test
    void instanceNotNull() {
        assertNotNull( pl,"Instance is null" );
    }

    /**
     * ensures usernames that are empty, start with a space or contain non-alphanumeric
     * characters are considered invalid
     */
    @Test
    void invalidName(){
        String empty = "";
        String invalidChars = "!*inVaLId*";
        String spaceStart = " inValid";

        boolean name1 = pl.isValid( empty );
        assertFalse( name1,"Empty string should not be a valid username" );

        boolean name2 = pl.isValid( invalidChars );
        assertFalse( name2, "Names with non-alphanumeric characters should not be a valid username" );

        boolean name3 = pl.isValid( spaceStart );
        assertFalse( name3, "Names that with a space should not be a valid username" );

    }

    /**
     * ensures usernames are at least one char, can contain alphanumeric chars and
     * can contain spaces anywhere except as the first char.
     */
    @Test
    void validName(){
        String message = "should be a valid name";
        String one_alpha = "a";
        String one_num = "1";
        String space = "The Kraken";
        String underscore = "the_kraken";
        String combo = "Cats637 38_";

        boolean name1 = pl.isValid( one_alpha );
        assertTrue( name1, one_alpha + message );

        boolean name2 = pl.isValid( one_num );
        assertTrue( name2, one_num + message );

        boolean name3 = pl.isValid( space );
        assertTrue( name3, space + message );

        boolean name4 = pl.isValid( underscore );
        assertTrue( name4, underscore + message );

        boolean name5 = pl.isValid( combo );
        assertTrue( name5, combo + message );

    }

    /**
     * test to make sure a new player can not use a name already in use
     */
    @Test
    void addDuplicateUsers(){
        String name = "abc";

        boolean firstAdd = pl.addPlayer( name );
        assertTrue( firstAdd, name + " should have been added here." );

        boolean secondAdd = pl.addPlayer( name );
        assertFalse( secondAdd, name + " should not have been added here." );
    }

    /**
     * tests that users with different names can be added
     */
    @Test
    void addUsers(){
        String message = "should have been added";
        String name1  = "The Kraken";
        String name2 = "Buttercup22";

        boolean add = pl.addPlayer( name1 );
        assertTrue( add, name1 + message );

        boolean add2 = pl.addPlayer( name2 );
        assertTrue( add2, name2 + message );
    }

    /**
     * tests if getPlayerCount is correct
     */
    @Test
    void PlayerCount(){
        Integer actual = pl.getPlayerCount();
        Integer expected = 3;
        assertEquals( expected, actual, "Size should be 3" );
    }

    @Test
    void getPlayers(){

    }
}