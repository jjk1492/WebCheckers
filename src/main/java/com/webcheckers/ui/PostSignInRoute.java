package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.SignInRenderer.MESSAGE_ATTR;
import static com.webcheckers.ui.SignInRenderer.MESSAGE_TYPE_ERROR;
import static com.webcheckers.ui.SignInRenderer.MESSAGE_TYPE_ATTR;
import static com.webcheckers.ui.WebServer.HOME_URL;

public class PostSignInRoute implements Route {

    static final String PLAYER_NAME_ATTR = "playerName";

    private static final String INVALID_NAME_MESSSAGE =
            "Your username couldn't be used as submitted.  Please submit a "
            + "username that starts with an alphanumeric character and "
            + "contains only alphanumerics and spaces.";

    private static final String NAME_TAKEN_MESSAGE =
            "Your username is already in use, please try a new one.";

    private Renderer signInRenderer;


    public PostSignInRoute( Renderer signInRenderer ) {
        this.signInRenderer = signInRenderer;
    }

    @Override
    public Object handle( Request request, Response response ) throws Exception {

        String playerName = request.queryParams( PLAYER_NAME_ATTR );

        if ( request.session().attribute( PLAYER_NAME_ATTR ) != null ) {
            response.redirect( HOME_URL );
            return null;
        }

        Map<String, Object> model = new HashMap<>();
        PlayerLobby lobby = PlayerLobby.getInstance();
        if ( !lobby.isValid( playerName ) ) {
            model.put( MESSAGE_TYPE_ATTR, MESSAGE_TYPE_ERROR );
            model.put( MESSAGE_ATTR, INVALID_NAME_MESSSAGE );
            return signInRenderer.render( request.session(), model );
        }
        else if ( !lobby.addPlayer( playerName ) ) {
            model.put( MESSAGE_TYPE_ATTR, MESSAGE_TYPE_ERROR );
            model.put( MESSAGE_ATTR, NAME_TAKEN_MESSAGE );
            return signInRenderer.render( request.session(), model );
        }
        else {
            final Session session = request.session();
            session.attribute( PLAYER_NAME_ATTR, playerName );
            response.redirect( HOME_URL );
            return null;
        }
    }
}
