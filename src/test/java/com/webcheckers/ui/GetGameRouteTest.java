package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
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
        renderer = mock(GameRenderer.class);
        getGameRoute = new GetGameRoute(renderer);
    }

    /**
     * tests to make sure a game can be created.
     */
    @Test
    public void new_game() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();

        when(renderer.render(any(Session.class))).thenAnswer(testHelper.makeAnswer());

        try {
            getGameRoute.handle(request, response);
        }catch(Exception e){}

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
    }

}
