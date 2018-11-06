package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * test for PostResignGameRoute
 *
 * @author Spencer Fleming
 */
@Tag("UI-tier")
public class PostResignGameRouteTest {

    private Request request;
    private Session session;
    private Response response;
    private GameCenter gameCenter;
    private Message message;
    private PostResignGameRoute postResignGameRoute;
    private final String redName = "red";
    private final String whiteName = "white";


    /**
     * create mock object
     */
    @BeforeEach
    public void setup() {
        message = mock(Message.class);
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gameCenter = mock(GameCenter.class);
        postResignGameRoute = new PostResignGameRoute(gameCenter);
    }

    /**
     * tests to make sure there are no errors when resigning from a game
     */
    @Test
    public void finishGame() {
        when(request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR)).thenReturn(redName);
        when(gameCenter.isPlayerInGame(redName)).thenReturn(false);

        try {
            Object ret = postResignGameRoute.handle(request, response);
            assertEquals("{\"type\":\"info\",\"text\":\"Resigned from game, please redirect to the home page!\"}", ret);
        }catch(Exception e){e.printStackTrace();}

        verify(gameCenter).finishedGame(redName);
    }

    /**
     * test to make sure an error message is sent when appropriate
     */
    @Test
    void errorMessage() {
        when(request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR)).thenReturn(redName);
        when(gameCenter.isPlayerInGame(redName)).thenReturn(true);

        try {
            Object ret = postResignGameRoute.handle(request, response);
            assertEquals("{\"type\":\"error\",\"text\":\"Could not resign from the game, please try again!\"}", ret);
        }catch(Exception e){e.printStackTrace();}

    }

}
