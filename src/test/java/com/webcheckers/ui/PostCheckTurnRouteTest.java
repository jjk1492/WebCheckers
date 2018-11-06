package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import spark.Request;
import spark.Response;
import spark.Session;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Zeke Miller
 */
public class PostCheckTurnRouteTest {

    private static final String redPlayerName = "red";
    private static final String whitePlayerName = "white";
    private static final Player redPlayer = mock( Player.class );
    private static final Player whitePlayer = mock( Player.class );
    private static final Game gameMock = mock( Game.class );
    private static final Response responseMock = mock( Response.class );
    private static final GameCenter centerMock = mock( GameCenter.class );
    private static final Session sessionMock = mock( Session.class );
    private static final Request requestMock = mock( Request.class );
    private static final Gson gson = new Gson();
    private static final Message trueMessage = new InfoMessage( "true" );
    private static final String trueJson = gson.toJson( trueMessage );
    private static final Message falseMessage = new InfoMessage( "false" );
    private static final String falseJson = gson.toJson( falseMessage );

    private PostCheckTurnRoute route;


    private void happySetup( Color active, String current ) {

        // getName same for all
        when( redPlayer.getName() ).thenReturn( redPlayerName );
        when( whitePlayer.getName() ).thenReturn( whitePlayerName );

        // getRedPlayer and getWhitePlayer same for all
        when( gameMock.getRedPlayer() ).thenReturn( redPlayer );
        when( gameMock.getWhitePlayer() ).thenReturn( whitePlayer );

        // shouldn't be accessing board
        when( gameMock.getRedBoard() ).thenReturn( null );
        when( gameMock.getWhiteBoard() ).thenReturn( null );

        // isPlayerInGame same for all
        when( centerMock.isPlayerInGame( redPlayerName ) ).thenReturn( true );
        when( centerMock.isPlayerInGame( whitePlayerName ) ).thenReturn( true );

        // getGame same for all
        when( centerMock.getGame( redPlayerName ) ).thenReturn( gameMock );
        when( centerMock.getGame( whitePlayerName ) ).thenReturn( gameMock );

        // getOpponent same for all
        when( centerMock.getOpponent( redPlayerName ) ).thenReturn( whitePlayerName );
        when( centerMock.getOpponent( whitePlayerName ) ).thenReturn( redPlayerName );

        // always returning the session mock
        when( requestMock.session() ).thenReturn( sessionMock );

        // return appropriate activity status
        when( centerMock.isPlayerActive( redPlayerName ) ).thenReturn( active.equals( Color.RED ) );
        when( centerMock.isPlayerActive( whitePlayerName ) ).thenReturn( active.equals( Color.WHITE ) );

        // return appropriate 'viewer' name
        when( sessionMock.attribute( PLAYER_NAME_ATTR ) ).thenReturn( current );

        route = new PostCheckTurnRoute( centerMock );
    }

    private String getJson() {
        String result = null;

        try {
            result = (String)route.handle( requestMock, responseMock );
        }
        catch ( Exception e ) {
            fail();
        }
        return result;
    }

    @Test
    public void testWhitePlayerWhiteTurn() {

        happySetup( Color.WHITE, whitePlayerName );

        String expected = trueJson;
        String actual = getJson();

        assertEquals( expected, actual, "Expected a true info message" );
    }

    @Test
    public void testWhitePlayerRedTurn() {

        happySetup( Color.RED, whitePlayerName );

        String expected = falseJson;
        String actual = getJson();

        assertEquals( expected, actual, "Expected a false info message" );
    }

    @Test
    public void testRedPlayerWhiteTurn() {

        happySetup( Color.WHITE, redPlayerName );

        String expected = falseJson;
        String actual = getJson();

        assertEquals( expected, actual, "Expected a false info message" );
    }

    @Test
    public void testRedPlayerRedTurn() {

        happySetup( Color.RED, redPlayerName );

        String expected = trueJson;
        String actual = getJson();

        assertEquals( expected, actual, "Expected a true info message" );
    }

    @Test
    public void testNullInConstructor() {

        Executable test = () -> new PostCheckTurnRoute( null );
        assertThrows( NullPointerException.class, test );

    }

    @Test
    public void testHappyConstructor() {
        route = new PostCheckTurnRoute( centerMock );
        assertNotNull( route );
    }
}
