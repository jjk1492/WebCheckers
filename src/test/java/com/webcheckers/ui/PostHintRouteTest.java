package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static com.webcheckers.model.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostHintRouteTest {

    private Request request;
    private Session session;
    private Response response;
    private GameCenter gameCenter;
    private Game game;
    private PostHintRoute postHintRoute;
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
        gameCenter = mock(GameCenter.class);
        game = mock(Game.class);
        postHintRoute = new PostHintRoute(gameCenter);
    }

    @Test
    void validRedMoveTest(){
        Move move = new Move(new Position(5,0), new Position(4,1));
        when(request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR)).thenReturn(redName);
        when(gameCenter.getGame(redName)).thenReturn(game);
        when(game.getValidMove()).thenReturn(move);

        try {
            Object ret = postHintRoute.handle(request, response);

            String expected = "You can move from " + move.toString();
            assertEquals("{\"type\":\"info\",\"text\":\"" + expected  + "\"}", ret);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    @Test
//    void validWhiteMoveTest(){
//        Move move = new Move(new Position(5,0), new Position(4,1));
//        when(request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR)).thenReturn(whiteName);
//        when(gameCenter.getGame(whiteName)).thenReturn(game);
//        when(game.getValidMove()).thenReturn(move);
//        when(game.getActiveColor()).thenReturn(WHITE);
//
//        try {
//            Object ret = postHintRoute.handle(request, response);
//            String expected = "You can move from " + move.toString();
//            assertEquals("{\"type\":\"info\",\"text\":\"" + expected  + "\"}", ret);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    /**
     * test if it returns the correct error message
     */
    @Test
    void errorTest(){
        when(request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR)).thenReturn(redName);
        when(gameCenter.getGame(redName)).thenReturn(game);
        when(game.getValidMove()).thenReturn(null);
        try {
            Object ret = postHintRoute.handle(request, response);
            assertEquals("{\"type\":\"error\",\"text\":\"Could not find a valid move\"}", ret);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
