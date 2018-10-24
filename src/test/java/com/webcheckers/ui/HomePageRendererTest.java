package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Zeke Miller
 */
public class HomePageRendererTest {

    @Test
    public void testNullTemplateEngine() {
        Executable testBothNull = () -> new HomePageRenderer( null, null );
        assertThrows( NullPointerException.class, testBothNull,
                      "expected an exception from null engine" );
        Executable testEngineNull =
                () -> new HomePageRenderer( null, new PlayerLobby() );
        assertThrows( NullPointerException.class, testEngineNull,
                      "expected an exception from null engine" );
    }
}
