package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

/**
 * @author Zeke Miller
 */
public class HomePageRendererTest {

    @Test
    public void testNullTemplateEngine() {
        Executable testBothNull = () -> new HomePageRenderer( null, null );
        assertThrows( NullPointerException.class, testBothNull,
                      "expected an exception from null arguments" );
        PlayerLobby lobby = new PlayerLobby();
        Executable testEngineNull = () -> new HomePageRenderer( null, lobby );
        assertThrows( NullPointerException.class, testEngineNull,
                      "expected an exception from null engine" );
    }

    @Test
    public void testNullPlayerLobby() {
        Executable testBothNull = () -> new HomePageRenderer( null, null );
        assertThrows( NullPointerException.class, testBothNull,
                      "expected an exception from null arguments" );

        TemplateEngine engine = mock(TemplateEngine.class);
        Executable testLobbyNull = () -> new HomePageRenderer( engine, null );
        assertThrows( NullPointerException.class, testLobbyNull,
                      "expected an exception from null lobby" );
    }
}
