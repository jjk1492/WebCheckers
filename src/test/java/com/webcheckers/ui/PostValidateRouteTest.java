package com.webcheckers.ui;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.InfoMessage;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.*;

@Tag("UI-Tier")
public class PostValidateRouteTest {

    private Request request;
    private Session session;
    private Move move;
    private Game game;
    private Response response;
    private GameCenter gameCenter;
    private PostValidateMove postValidateMove;
    private final String redName = "red";
    private final String whiteName = "white";


    /**
     * create mock object
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        move = mock(Move.class);
        game = mock(Game.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        gameCenter = mock(GameCenter.class);
        postValidateMove = new PostValidateMove(gameCenter);
    }

    /**
     * tests to make sure there are no errors when submitting a turn
     */
    @Test
    public void validateMove() {
        when(request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR)).thenReturn(redName);

        when(gameCenter.getGame(redName)).thenReturn(game);
        when(game.tryMove(any())).thenReturn(new InfoMessage("test"));

        try {
            Object ret = postValidateMove.handle(request, response);
            assertEquals("{\"type\":\"info\",\"text\":\"test\"}", ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
