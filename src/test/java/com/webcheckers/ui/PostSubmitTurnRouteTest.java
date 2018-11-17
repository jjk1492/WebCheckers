package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PostSubmitTurnRouteTest {

    private Request request;
    private Session session;
    private Response response;
    private GameCenter gameCenter;
    private Message message;
    private PostSubmitTurnRoute postSubmitTurnRoute;
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
        postSubmitTurnRoute = new PostSubmitTurnRoute(gameCenter);
    }

    /**
     * tests to make sure there are no errors when submitting a turn
     */
    @Test
    public void submitTurn() {
        when(request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR)).thenReturn(redName);
        when(gameCenter.isPlayerInGame(redName)).thenReturn(true);
        when(gameCenter.finishTurn( redName )).thenReturn(true);
        when(gameCenter.isPlayerActive(redName)).thenReturn(false);

        try {
            Object ret = postSubmitTurnRoute.handle(request, response);
            assertEquals("{\"type\":\"info\",\"text\":\"Turn submitted!\"}", ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * test to make sure an error message is sent when appropriate
     */
    @Test
    void errorMessage() {
        when(request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR)).thenReturn(redName);
        when(gameCenter.isPlayerInGame(redName)).thenReturn(true);
        when(gameCenter.finishTurn( redName )).thenReturn(true);
        when(gameCenter.isPlayerActive(redName)).thenReturn(true);

        try {
            Object ret = postSubmitTurnRoute.handle(request, response);
            assertEquals("{\"type\":\"error\",\"text\":\"Could not submit turn, please try again!\"}", ret);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * test to make sure it can handle when a game has ended
     */
    @Test
    void gameEnded() {
        when(request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR)).thenReturn(redName);
        when(gameCenter.isPlayerInGame(redName)).thenReturn(false);
        when(gameCenter.finishTurn( redName )).thenReturn(true);

        try {
            Object ret = postSubmitTurnRoute.handle(request, response);
            assertEquals("{\"type\":\"info\",\"text\":\"The game has ended, please redirect to home!\"}", ret);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void forceJumpMessage(){
        when(request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR)).thenReturn(redName);
        when(gameCenter.isPlayerInGame(redName)).thenReturn(true);
        when(gameCenter.finishTurn( redName )).thenReturn(false);
        try {
            Object ret = postSubmitTurnRoute.handle(request, response);
            assertEquals("{\"type\":\"error\",\"text\":\"You still have a jump you need to make!\"}", ret);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}