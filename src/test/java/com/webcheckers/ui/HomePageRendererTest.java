package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import spark.Session;
import spark.TemplateEngine;

import java.util.*;

import static com.webcheckers.ui.HomePageRenderer.*;
import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Zeke Miller
 */
@Tag("UI-Tier")
public class HomePageRendererTest {

    private static final String PLAYER1 = "player1";
    private static final String PLAYER2 = "player2";
    private static final Collection<String> PLAYERS =
            Arrays.asList( PLAYER1, PLAYER2 );
    private static final String NAME1 = "name1";
    private static final String NAME2 = "name2";

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

    @Test
    public void testRenderNullSession() {
        PlayerLobby lobbyMock = mock( PlayerLobby.class );
        TemplateEngine engineMock = mock( TemplateEngine.class );
        Executable test =
                () -> new HomePageRenderer( engineMock, lobbyMock )
                        .render( null );

        assertThrows( NullPointerException.class, test,
                      "expected an exception from null Session" );
    }

    private TemplateEngineTester test( TestSettings settings ) {

        PlayerLobby lobbyMock = mock( PlayerLobby.class );
        if ( settings.nullPlayer ) {
            when( lobbyMock.getPlayer( settings.playerName ) ).thenReturn( null );
            when( lobbyMock.getPlayer( settings.sessionName ) ).thenReturn( null );
        }
        else {
            Player playerMock = mock( Player.class );
            when( playerMock.getName() ).thenReturn( settings.playerName );
            when( lobbyMock.getPlayer( settings.playerName ) ).thenReturn( playerMock );
            when( lobbyMock.getPlayer( settings.sessionName ) ).thenReturn( playerMock );
        }

        when( lobbyMock.getAllPlayers() ).thenReturn( settings.players );

        TemplateEngineTester tester = new TemplateEngineTester();
        TemplateEngine engineMock = mock( TemplateEngine.class );
        when( engineMock.render( Mockito.any() ) ).then( tester.makeAnswer() );

        Session sessionMock = mock( Session.class );
        when( sessionMock.attribute( PLAYER_NAME_ATTR ) ).thenReturn( settings.sessionName );

        Renderer renderer = new HomePageRenderer( engineMock, lobbyMock );
        if ( settings.provideMap ) {
            renderer.render( sessionMock, settings.map );
        }
        else {
            renderer.render( sessionMock );
        }

        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();
        tester.assertViewName( HOME_VIEW_NAME );
        tester.assertViewModelAttribute( PLAYER_NAME_ATTR, settings.expectedName );
        tester.assertViewModelAttribute( SIGNED_IN_ATTR, settings.signInExpected );
        tester.assertViewModelAttribute( PLAYER_LIST_ATTR, settings.players );
        return tester;
    }

    private class TestSettings {
        private String sessionName = null;
        private String playerName = null;
        boolean nullPlayer = false;
        private String expectedName = null;
        private Collection<String> players = null;
        boolean signInExpected = false;
        Map<String, Object> map = null;
        boolean provideMap = false;

        private void setNames( String name ) {
            sessionName = playerName = expectedName = name;
        }
    }

    // normalish ones

    @Test
    public void testRenderSignedInNormal() {
        TestSettings settings = new TestSettings();
        settings.setNames( NAME1 );
        settings.players = PLAYERS;
        settings.signInExpected = true;
        test( settings );
    }

    @Test
    public void testRenderNotSignedIn() {
        TestSettings settings = new TestSettings();
        settings.setNames( null );
        settings.players = PLAYERS;
        settings.signInExpected = false;
        test( settings );
    }

    @Test
    public void testNullPlayerNullName() {
        TestSettings settings = new TestSettings();
        settings.setNames( null );
        settings.players = PLAYERS;
        settings.signInExpected = false;
        settings.nullPlayer = true;
        test( settings );
    }

    @Test
    public void testNullPlayerMatchName() {
        TestSettings settings = new TestSettings();
        settings.setNames( NAME1 );
        settings.expectedName = null;
        settings.players = PLAYERS;
        settings.signInExpected = false;
        settings.nullPlayer = true;
        test( settings );
    }

    @Test
    public void testNullPlayerMisMatchName() {
        TestSettings settings = new TestSettings();
        settings.sessionName = NAME1;
        settings.playerName = NAME2;
        settings.expectedName = null;
        settings.players = PLAYERS;
        settings.signInExpected = false;
        settings.nullPlayer = true;
        test( settings );
    }


    @Test
    public void testNullPlayersFromLobby() {
        TestSettings settings = new TestSettings();
        settings.setNames( NAME1 );
        settings.signInExpected = true;
        test( settings );
    }


    @Test
    public void testEmptyPlayerList() {
        TestSettings settings = new TestSettings();
        settings.setNames( NAME1 );
        settings.players = new ArrayList<>();
        settings.signInExpected = true;
        test( settings );
    }


    // weird map tests

    @Test
    public void testRenderNormalNullMap() {
        TestSettings settings = new TestSettings();
        settings.setNames( NAME1 );
        settings.players = PLAYERS;
        settings.signInExpected = true;
        settings.map = null;
        settings.provideMap = true;
        test( settings );
    }


    @Test
    public void testNotOverwritingTitle() {
        final String title = "testing Title";
        Map<String, Object> map = new HashMap<>();
        map.put( TITLE_ATTR, title );

        TemplateEngineTester tester;

        TestSettings settings = new TestSettings();
        settings.setNames( NAME1 );
        settings.players = PLAYERS;
        settings.signInExpected = true;
        settings.map = map;
        settings.provideMap = true;
        tester = test( settings );

        tester.assertViewModelAttribute( TITLE_ATTR, title );
    }


    // weird name tests

    @Test
    public void testSessionNullName() {

        TestSettings settings = new TestSettings();
        settings.setNames( null );
        settings.playerName = NAME1;
        settings.players = PLAYERS;
        settings.signInExpected = false;
        test( settings );
    }


    @Test
    public void testLobbyNullName() {
        TestSettings settings = new TestSettings();
        settings.setNames( null );
        settings.sessionName = NAME1;
        settings.players = PLAYERS;
        settings.signInExpected = false;
        test( settings );
    }


    @Test
    public void testMismatchNameError() {
        TestSettings settings = new TestSettings();
        settings.sessionName = NAME1;
        settings.playerName = NAME2;
        settings.expectedName = null;
        settings.players = PLAYERS;
        settings.signInExpected = false;
        test( settings );
    }

}
