package com.webcheckers.ui;

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
 * @author Spencer Fleming
 */
@Tag("UI-tier")
public class GetGameRouteTest {

    private Request request;
    private Session session;
    private Response response;
    private Renderer renderer;
    private GameCenter gameCenter;
    private PlayerLobby playerLobby;
    private GetGameRoute getGameRoute;
    private final String redName = "red";
    private final String whiteName = "white";

    /**
     * create mock object
     */
    @BeforeEach
    public void setup() {

        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        playerLobby = new PlayerLobby();
        gameCenter = new GameCenter(playerLobby);
        playerLobby.addPlayer(redName);
        playerLobby.addPlayer(whiteName);
        renderer = mock(GameRenderer.class);

        getGameRoute = new GetGameRoute(renderer,gameCenter);
    }

    /**
     * tests to make sure there are no errors with a valid game
     */
    @Test
    public void validGame() {
        gameCenter.addGame(redName,whiteName);

        when(request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR)).thenReturn(redName);

        try {
            getGameRoute.handle(request, response);
        }catch(Exception e){e.printStackTrace();}

        verify(renderer).render(any(Session.class), any(Map.class));

    }

    /**
     * test to make sure GetGameRoute redirects to home when the game was not created
     */
    @Test
    void invalidGame() {
        gameCenter.finishedGame(redName,whiteName);
        when(request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR)).thenReturn(redName);

        try {
            getGameRoute.handle(request, response);
        }catch(Exception e){e.printStackTrace();}

        verify(response, atLeastOnce()).redirect(WebServer.HOME_URL);
    }

    /**
     * test to make sure GetGameRoute redirects to home when the players name is null
     */
    @Test
    void invalidName() {
        when(request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR)).thenReturn(null);

        try {
            getGameRoute.handle(request, response);
        }catch(Exception e){e.printStackTrace();}

        verify(response).redirect(WebServer.HOME_URL);
    }



}

