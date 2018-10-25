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


    private TemplateEngineTester test( String name, Collection<String> players,
                                       boolean signInExpected ) {

        return test( name, name, name, players, signInExpected );
    }


    private TemplateEngineTester test( String name, Collection<String> players,
                                       boolean signInExpected, Map<String,
                                       Object> map, boolean provideMap ) {
        return test( name, name, name, players, signInExpected, map, provideMap );
    }


    private TemplateEngineTester test( String sessionName, String playerName,
                       String expectedName, Collection<String> players,
                       boolean signInExpected ) {

        return test( sessionName, playerName, expectedName,
              players, signInExpected, null, false );

    }


    private TemplateEngineTester test( String sessionName, String playerName,
                                       String expectedName, Collection<String> players,
                                       boolean signInExpected, Map<String, Object> map,
                                       boolean provideMap ) {

        Player playerMock = mock( Player.class );
        when( playerMock.getName() ).thenReturn( playerName );

        PlayerLobby lobbyMock = mock( PlayerLobby.class );
        when( lobbyMock.getPlayer( playerName ) ).thenReturn( playerMock );
        when( lobbyMock.getAllPlayers() ).thenReturn( players );

        TemplateEngineTester tester = new TemplateEngineTester();
        TemplateEngine engineMock = mock( TemplateEngine.class );
        when( engineMock.render( Mockito.any() ) ).then( tester.makeAnswer() );

        Session sessionMock = mock( Session.class );
        when( sessionMock.attribute( PLAYER_NAME_ATTR ) ).thenReturn( sessionName );

        Renderer renderer = new HomePageRenderer( engineMock, lobbyMock );
        if ( provideMap ) {
            renderer.render( sessionMock, map );
        }
        else {
            renderer.render( sessionMock );
        }

        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();
        tester.assertViewName( HOME_VIEW_NAME );
        tester.assertViewModelAttribute( PLAYER_NAME_ATTR, expectedName );
        tester.assertViewModelAttribute( SIGNED_IN_ATTR, signInExpected );
        tester.assertViewModelAttribute( PLAYER_LIST_ATTR, players );
        return tester;
    }

    // normalish ones

    @Test
    public void testRenderSignedInNormal() {
        test( NAME1, PLAYERS, true );
    }

    @Test
    public void testRenderNotSignedIn() {
        test( null, PLAYERS, false );
    }


    @Test
    public void testNullPlayersFromLobby() {
        test( NAME1, null, true );
    }


    @Test
    public void testEmptyPlayerList() {
        test( NAME1, new ArrayList<>(), true );
    }


    // weird map tests

    @Test
    public void testRenderNormalNullMap() {
        test( NAME1, PLAYERS, true, null, true );
    }


    @Test
    public void testNotOverwritingTitle() {
        final String title = "testing Title";
        Map<String, Object> map = new HashMap<>();
        map.put( TITLE_ATTR, title );

        TemplateEngineTester tester;
        tester = test( NAME1, PLAYERS, true, map, true );

        tester.assertViewModelAttribute( TITLE_ATTR, title );
    }


    // weird name tests

    @Test
    public void testSessionNullName() {
        test( null, NAME1, null, PLAYERS, false );
    }


    @Test
    public void testLobbyNullName() {
        test( NAME1, null, null, PLAYERS, false );
    }


    @Test
    public void testMismatchNameError() {
        test( NAME1, NAME2, null, PLAYERS, false );
    }


}
