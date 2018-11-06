package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostSignInRouteTest {

    private Request request;
    private Session session;
    private Response response;
    private Renderer renderer;
    private PlayerLobby playerLobby;
    private PostSignInRoute postSignInRoute;

    /**
     * create mock object
     */
    @BeforeEach
    public void setup() {

        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        playerLobby = mock(PlayerLobby.class);
        renderer = mock(HomePageRenderer.class);
        postSignInRoute = new PostSignInRoute( renderer, playerLobby );
    }

}
