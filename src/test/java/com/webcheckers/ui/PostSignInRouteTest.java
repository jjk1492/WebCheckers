package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.Map;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;
import static com.webcheckers.ui.WebServer.HOME_URL;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PostSignInRouteTest {

    private Request request;
    private Session session;
    private Response response;
    private Renderer renderer;
    private PlayerLobby playerLobby;
    private PostSignInRoute postSignInRoute;

    /**
     * create mock object
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        playerLobby = mock(PlayerLobby.class);
        renderer = mock(HomePageRenderer.class);
        postSignInRoute = new PostSignInRoute( renderer, playerLobby );
    }

    @Test
    public void happySignIn() {
        String playerName = "BigPlayer";

        when( request.queryParams( PLAYER_NAME_ATTR ) ).thenReturn( playerName );
        when( session.attribute( PLAYER_NAME_ATTR ) ).thenReturn( null );
        when( playerLobby.isValid(playerName) ).thenReturn(true);
        when( playerLobby.addPlayer(playerName)).thenReturn(true);

        try {
            postSignInRoute.handle(request, response);
        }
        catch ( Exception e ) {
            fail();
        }

        verify(playerLobby).addPlayer(playerName);
        verify(session).attribute(PLAYER_NAME_ATTR, playerName);
        verify(response).redirect(HOME_URL);
    }

    @Test
    public void alreadySignedIn() {
        String playerName = "SmallPlayer";

        when( request.queryParams( PLAYER_NAME_ATTR ) ).thenReturn( playerName );
        when( session.attribute( PLAYER_NAME_ATTR ) ).thenReturn( playerName );
        when( playerLobby.isValid(playerName) ).thenReturn(true);
        when( playerLobby.addPlayer(playerName)).thenReturn(false);

        try {
            postSignInRoute.handle(request,response);
        }
        catch ( Exception e ) {
            fail();
        }
    }

    @Test
    public void notValidName(){
        String playerName = "***";
        when( request.queryParams( PLAYER_NAME_ATTR ) ).thenReturn( playerName );
        when( session.attribute( PLAYER_NAME_ATTR ) ).thenReturn( playerName );
        when( playerLobby.isValid(playerName) ).thenReturn(false);

        try {
            postSignInRoute.handle(request, response);
        }
        catch ( Exception e ) {
            fail();
        }

    }
}
