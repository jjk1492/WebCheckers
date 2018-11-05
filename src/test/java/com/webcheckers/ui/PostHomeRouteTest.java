package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * test for PostHomeRoute
 *
 * @author Spencer Fleming
 */
@Tag("UI-tier")
public class PostHomeRouteTest {

    private Request request;
    private Session session;
    private Response response;
    private GameCenter gameCenter;
    private Renderer renderer;
    private PostHomeRoute postHomeRoute;
    private final String redName = "red";
    private final String whiteName = "white";


    /**
     * create mock object
     */
    @BeforeEach
    public void setup() {
        renderer = mock(HomePageRenderer.class);
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gameCenter = mock(GameCenter.class);
        postHomeRoute = new PostHomeRoute(renderer, gameCenter);
    }

    /**
     * tests to make sure it redirects to game
     */
    @Test
    public void redirectToGame() {
        when(request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR)).thenReturn(redName);
        when(request.queryParams( "opponent" )).thenReturn(whiteName);
        when(gameCenter.isPlayerInGame(redName)).thenReturn(false);
        when(gameCenter.isPlayerInGame(whiteName)).thenReturn(false);
        when(gameCenter.addGame(redName,whiteName)).thenReturn(true);

        try {
            postHomeRoute.handle(request, response);
        }catch(Exception e){e.printStackTrace();}

        verify(response).redirect(WebServer.GAME_URL);
    }

    /**
     * tests to make sure it gives an error message with an invalid player
     */
    @Test
    public void invalidPlayer() {
        when(request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR)).thenReturn(null);

        try {
            postHomeRoute.handle(request, response);
        }catch(Exception e){e.printStackTrace();}

        verify(renderer).render(any(Session.class), any(Map.class));
    }

    /**
     * tests to make sure it gives an error message with an invalid opponent
     */
    @Test
    public void invalidOpponent() {
        when(request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR)).thenReturn(redName);
        when(request.queryParams( "opponent" )).thenReturn(null);

        try {
            postHomeRoute.handle(request, response);
        }catch(Exception e){e.printStackTrace();}

        verify(renderer).render(any(Session.class), any(Map.class));
    }

    /**
     * tests to make sure it gives an error message with the player already in a game
     */
    @Test
    public void playerInGame() {
        when(request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR)).thenReturn(redName);
        when(request.queryParams( "opponent" )).thenReturn(whiteName);
        when(gameCenter.isPlayerInGame(redName)).thenReturn(true);

        try {
            postHomeRoute.handle(request, response);
        }catch(Exception e){e.printStackTrace();}

        verify(renderer).render(any(Session.class), any(Map.class));
    }

    /**
     * tests to make sure it gives an error message with the opponent already in a game
     */
    @Test
    public void opponentInGame() {
        when(request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR)).thenReturn(redName);
        when(request.queryParams( "opponent" )).thenReturn(whiteName);
        when(gameCenter.isPlayerInGame(redName)).thenReturn(false);
        when(gameCenter.isPlayerInGame(whiteName)).thenReturn(true);

        try {
            postHomeRoute.handle(request, response);
        }catch(Exception e){e.printStackTrace();}

        verify(renderer).render(any(Session.class), any(Map.class));
    }

    /**
     * tests to make sure it gives an error message with an invalid game
     */
    @Test
    public void invalidGame() {
        when(request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR)).thenReturn(redName);
        when(request.queryParams( "opponent" )).thenReturn(whiteName);
        when(gameCenter.isPlayerInGame(redName)).thenReturn(false);
        when(gameCenter.isPlayerInGame(whiteName)).thenReturn(false);
        when(gameCenter.addGame(redName,whiteName)).thenReturn(false);

        try {
            postHomeRoute.handle(request, response);
        }catch(Exception e){e.printStackTrace();}

        verify(renderer).render(any(Session.class), any(Map.class));
    }
}
