package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostBackupMoveTest {

    private Request request;
    private Session session;
    private Response response;
    private GameCenter gameCenter;
    private Game game;
    private PlayerLobby playerLobby;
    private Message message;
    private PostBackupMove postBackupMove;
    private final String redName = "red";
    private Move move;


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
        game = mock(Game.class);
        postBackupMove = new PostBackupMove(gameCenter);
        move = mock(Move.class);
        playerLobby = mock(PlayerLobby.class);
    }

    /**
     * tests to make sure there are no errors when backing up after a move has been made
     */
    @Test
    public void backupTurnInfo() {
        when(request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR)).thenReturn(redName);
        when(gameCenter.getGame(redName)).thenReturn(game);
        when(game.backupMove()).thenReturn((true));

        try {
            Object ret = postBackupMove.handle(request, response);
            assertEquals("{\"type\":\"info\",\"text\":\"Reset board to your last valid move\"}", ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * test to see if error message is being sent when needed.
     */
    @Test
    public void backupTurnError() {
        when(request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR)).thenReturn(redName);
        when(gameCenter.getGame(redName)).thenReturn(game);
        when(game.backupMove()).thenReturn((false));

        try {
            Object ret = postBackupMove.handle(request, response);
            assertEquals("{\"type\":\"error\",\"text\":\"There was no move to backup to!\"}", ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}