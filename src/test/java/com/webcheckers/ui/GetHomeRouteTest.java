package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.webcheckers.application.GameCenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.*;

import java.util.Map;

/**
 * test for GetHomeRoute
 *
 * @author Nick Sander
 */
@Tag("UI-tier")
public class GetHomeRouteTest {

    private Request request;
    private Session session;
    private Response response;
    private Renderer renderer;
    private GameCenter gameCenter;
    private GetHomeRoute getHomeRoute;

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
        getHomeRoute = new GetHomeRoute(renderer, gameCenter);
    }

    /**
     * tests to make sure there are no errors with a valid home page
     */
    @Test
    public void validHome() {
        String playerName = request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR);
        System.out.println(" Returning player name : " + playerName);
        when(playerName != null).thenReturn(false);
        when(gameCenter.isPlayerInGame(playerName)).thenReturn(false);

        try {
            getHomeRoute.handle(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(renderer).render(any(Session.class), any(Map.class));
    }


}
