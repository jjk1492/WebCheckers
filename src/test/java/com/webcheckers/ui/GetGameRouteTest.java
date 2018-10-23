package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.*;

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

    /**
     * create mock object
     */
    @BeforeEach
    public void setup() {

        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gameCenter = GameCenter.getInstance();
        playerLobby = PlayerLobby.getInstance();
        renderer = mock(GameRenderer.class);

        getGameRoute = new GetGameRoute(renderer);
    }

    /**
     * tests to make sure a game can be created.
     */
    @Test
    public void new_game() {
        final String redName = "red";
        final String whiteName = "white";
        playerLobby.addPlayer(redName);
        playerLobby.addPlayer(whiteName);
        when(request.session().attribute("name")).thenReturn(redName);
        when(request.queryParams("opponent")).thenReturn(whiteName);

        try {
            getGameRoute.handle(request, response);
        }catch(Exception e){e.printStackTrace();}

        boolean redPlayer = gameCenter.isPlayerInGame(redName);
        assertTrue( redPlayer, redName + " should be in a game." );

        boolean whitePlayer = gameCenter.isPlayerInGame(whiteName);
        assertTrue( whitePlayer, whiteName + " should be in a game." );


    }

}
