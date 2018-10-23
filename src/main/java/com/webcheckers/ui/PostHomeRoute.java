package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.SignInRenderer.MESSAGE_ATTR;
import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;
import static com.webcheckers.model.Message.Type.ERROR;
import static com.webcheckers.ui.SignInRenderer.MESSAGE_TYPE_ATTR;
import static com.webcheckers.ui.SignInRenderer.MESSAGE_TYPE_ERROR;
import static com.webcheckers.ui.WebServer.GAME_URL;

/**
 * @author Zeke Miller
 */
public class PostHomeRoute
        implements Route {

    private static final String BAD_OPPONENT_ERROR = "Opponent doesn't exist!";
    private static final String NOT_SIGNED_IN_ERROR =
            "You must be signed in to start a game!";
    private static final String ALREADY_IN_GAME_ERROR =
            "You're already in a game!";
    private static final String OPPONENT_IN_GAME_ERROR =
            "That player is already in a game!";
    private static final String GAME_CREATION_ERROR =
            "Couldn't create a game, please try again!";

    private static final String OPPONENT_PARAM = "opponent";


    private final Renderer renderer;


    public PostHomeRoute( Renderer renderer ) {
        this.renderer = renderer;
    }

    @Override
    public Object handle( Request request, Response response ) {

        String name = request.session().attribute( PLAYER_NAME_ATTR );
        String opponentName = request.queryParams( OPPONENT_PARAM );
        GameCenter gameCenter = GameCenter.getInstance();

        Map<String, Object> map = new HashMap<>();
        if ( name == null ) {
            map.put( MESSAGE_ATTR, new Message( NOT_SIGNED_IN_ERROR, ERROR ) );
            return renderer.render( request.session(), map );
        }
        else if ( opponentName == null ) {
            map.put( MESSAGE_ATTR, new Message( BAD_OPPONENT_ERROR, ERROR ) );
            return renderer.render( request.session(), map );
        }
        else if ( gameCenter.isPlayerInGame( name ) ) {
//            map.put( MESSAGE_TYPE_ATTR, MESSAGE_TYPE_ERROR );
            map.put( MESSAGE_ATTR, new Message( ALREADY_IN_GAME_ERROR, ERROR ) );
//            map.put( MESSAGE_ATTR, ALREADY_IN_GAME_ERROR );
            return renderer.render( request.session(), map );
        }
        else if ( gameCenter.isPlayerInGame( opponentName ) ) {
            map.put( MESSAGE_ATTR, new Message( OPPONENT_IN_GAME_ERROR, ERROR ) );
            return renderer.render( request.session(), map );
        }
        else if ( !gameCenter.addGame(name, opponentName) ) {
            map.put( MESSAGE_ATTR, new Message( GAME_CREATION_ERROR, ERROR ) );
            return renderer.render( request.session(), map );
        }
        else {
            response.redirect( GAME_URL );
            return null;
        }
    }
}
