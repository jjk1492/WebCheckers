package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.ErrorMessage;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.SignInRenderer.MESSAGE_ATTR;
import static com.webcheckers.ui.WebServer.HOME_URL;

/**
 * POST /signin handler (signing a player in)
 *
 * @author Zeke Miller
 */
public class PostSignInRoute implements Route {

    // private constants

    private static final String INVALID_NAME_MESSSAGE =
            "Your username couldn't be used as submitted.  Please submit a "
            + "username that starts with an alphanumeric character and "
            + "contains only alphanumerics and spaces.";

    private static final String NAME_TAKEN_MESSAGE =
            "Your username is already in use, please try a new one.";


    // package private constants

    static final String PLAYER_NAME_ATTR = "name";


    // fields

    private Renderer renderer;


    // constructors

    /**
     * constructor, requires a Renderer for dependency inversion
     * @param renderer the rendering engine
     */
    public PostSignInRoute( Renderer renderer ) {
        this.renderer = renderer;
    }


    // methods

    /**
     * handles a POST /signin (a player signing in)
     * @param request the HTTP request spark object
     * @param response the response spark object
     * @return rendered page
     * @throws Exception shouldn't throw exceptions, but if the rendering
     * engine fails we simply rethrow
     */
    @Override
    public Object handle( Request request, Response response ) throws Exception {

        if ( request.session().attribute( PLAYER_NAME_ATTR ) != null ) {
            response.redirect( HOME_URL );
            return null;
        }

        String name = request.queryParams( PLAYER_NAME_ATTR );

        Map<String, Object> model = new HashMap<>();
        PlayerLobby lobby = PlayerLobby.getInstance();

        if ( !lobby.isValid( name ) ) {
            model.put( MESSAGE_ATTR,
                       new ErrorMessage( INVALID_NAME_MESSSAGE ) );
            return renderer.render( request.session(), model );
        }
        else if ( !lobby.addPlayer( name ) ) {
            model.put( MESSAGE_ATTR, new ErrorMessage( NAME_TAKEN_MESSAGE ) );
            return renderer.render( request.session(), model );
        }
        else {
            final Session session = request.session();
            session.attribute( PLAYER_NAME_ATTR, name );
            // TODO should we be storing the Player or the name?
            response.redirect( HOME_URL );
            return null;
        }
    }
}
