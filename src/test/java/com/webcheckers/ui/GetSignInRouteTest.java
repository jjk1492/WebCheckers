package com.webcheckers.ui;

import static com.webcheckers.ui.WebServer.HOME_URL;
import static org.junit.jupiter.api.Assertions.fail;
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
 * @author Nick Sander
 */
@Tag("UI-tier")
public class GetSignInRouteTest {

    private Request request;
    private Session session;
    private Response response;
    private Renderer renderer;
    private GetSignInRoute getSignInRoute;

    /**
     * create mock object
     */
    @BeforeEach
    public void setup() {

        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        renderer = mock(HomePageRenderer.class);
        getSignInRoute = new GetSignInRoute(renderer);
    }

    /**
     * tests to make sure there are no errors with a valid home page
     */
    @Test
    public void alreadySignedIn() throws Exception {
        String playerName = "player";
        when( request.session().attribute(PostSignInRoute.PLAYER_NAME_ATTR) )
                .thenReturn( playerName );

        getSignInRoute.handle( request, response );
        verify(response).redirect( HOME_URL );
    }

    @Test
    public void notSignedInYet() throws Exception {
        getSignInRoute.handle( request, response );
        verify( response, never() ).redirect( any() );
    }



}

