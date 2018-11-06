package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.*;

import java.util.Map;

/**
 * test for GetGameRoute
 *
 * @author Nick Sander
 */
@Tag("UI-tier")
public class GetSignOutRouteTest {

    private Request request;
    private Session session;
    private Response response;
    private Renderer renderer;
    private GameCenter gameCenter;
    private GetSignOutRoute getSignOutRoute;

    /**
     * create mock object
     */
    @BeforeEach
    public void setup() {

        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gameCenter = mock(GameCenter.class);
        renderer = mock(HomePageRenderer.class);
        getSignOutRoute = new GetSignOutRoute(renderer,gameCenter);
    }

    /**
     * tests to make sure there are no errors with a valid home page
     */
    @Test
    public void validHome() {
        String playerName = "player";
        when( request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR) )
                .thenReturn( playerName );
        when(gameCenter.isPlayerInGame(playerName)).thenReturn(false);

        try {
            getSignOutRoute.handle(request, response);
        } catch (Exception e) {
            fail();
        }
        verify(gameCenter).removePlayer(playerName);
    }



}

