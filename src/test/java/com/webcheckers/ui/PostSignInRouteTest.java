package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import spark.*;

import java.util.Map;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;
import static com.webcheckers.ui.SignInRenderer.MESSAGE_ATTR;
import static com.webcheckers.ui.WebServer.HOME_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PostSignInRouteTest {

    private Request request;
    private Session session;
    private Response response;
    private Renderer renderer;
    private PlayerLobby playerLobby;
    private PostSignInRoute postSignInRoute;

    private Answer<Object> assertErrorMessage = new Answer<Object>() {
        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable {
            if ( invocation.getArguments().length != 2 ) {
                fail();
            }
            Map<String, Object> map = invocation.getArgument( 1 );
            assertTrue( map.containsKey( MESSAGE_ATTR ) );
            assertTrue( map.get( MESSAGE_ATTR ) instanceof Message );
            Message message = (Message)map.get( MESSAGE_ATTR );
            assertEquals( Message.Type.error, message.getType() );
            return null;
        }
    };

    /**
     * create mock object
     */
    @BeforeEach
    public void setup() {
        TemplateEngineTester tester = new TemplateEngineTester();
        TemplateEngine engineMock = mock( TemplateEngine.class );
        when( engineMock.render( Mockito.any() ) ).then( tester.makeAnswer() );
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        playerLobby = mock(PlayerLobby.class);
        renderer = mock(HomePageRenderer.class);
        postSignInRoute = new PostSignInRoute( renderer, playerLobby );
    }

    @Test
    public void happySignIn() {
        String playerName = "BigPlayer";

        when( request.queryParams( PLAYER_NAME_ATTR ) ).thenReturn( playerName );
        when( session.attribute( PLAYER_NAME_ATTR ) ).thenReturn( null );
        when( playerLobby.isValid(playerName) ).thenReturn(true);
        when( playerLobby.addPlayer(playerName)).thenReturn(true);

        try {
            postSignInRoute.handle(request, response);
        }
        catch ( Exception e ) {
            fail();
        }

        verify(playerLobby).addPlayer(playerName);
        verify(session).attribute(PLAYER_NAME_ATTR, playerName);
        verify(response).redirect(HOME_URL);
    }

    @Test
    public void alreadySignedIn() {
        String playerName = "SmallPlayer";

        when( request.queryParams( PLAYER_NAME_ATTR ) ).thenReturn( playerName );
        when( session.attribute( PLAYER_NAME_ATTR ) ).thenReturn( playerName );
        when( playerLobby.isValid(playerName) ).thenReturn(true);
        when( playerLobby.addPlayer(playerName)).thenReturn(false);

        try {
            postSignInRoute.handle(request,response);
        }
        catch ( Exception e ) {
            fail();
        }
    }

    @Test
    public void notValidName(){
        String playerName = "***";
        when( request.queryParams( PLAYER_NAME_ATTR ) ).thenReturn( playerName );
        when( session.attribute( PLAYER_NAME_ATTR ) ).thenReturn( null );
        when( playerLobby.isValid(playerName) ).thenReturn(false);

        when( renderer.render( any() ) ).then(assertErrorMessage);
        when( renderer.render( any(), any() ) ).then(assertErrorMessage);

        try {
            postSignInRoute.handle(request, response);
        }
        catch ( Exception e ) {
            fail();
        }
        verify(playerLobby).isValid(playerName);
        verify(playerLobby, never()).addPlayer(playerName);
    }

    @Test
    public void nameTaken() {
        String playerName = "BigPlayer";
        Player alreadySignedIn = mock(Player.class);
        when(alreadySignedIn.getName()).thenReturn(playerName);

        when( request.queryParams( PLAYER_NAME_ATTR ) ).thenReturn( playerName );
        when( session.attribute( PLAYER_NAME_ATTR ) ).thenReturn( null );
        when( playerLobby.isValid(playerName) ).thenReturn(true);
        when( playerLobby.addPlayer(playerName)).thenReturn(false);
        when(playerLobby.getPlayer("BigPlayer")).thenReturn(alreadySignedIn);

        when( renderer.render( any() ) ).then(assertErrorMessage);
        when( renderer.render( any(), any() ) ).then(assertErrorMessage);

        try {
            postSignInRoute.handle(request, response);
        }
        catch ( Exception e ) {
            fail();
        }

        verify(playerLobby).addPlayer(playerName);
        verify(session, never()).attribute(PLAYER_NAME_ATTR, playerName);
        verify(response, never()).redirect(HOME_URL);
    }
}
