package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import spark.Request;
import spark.Response;
import spark.Route;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;
import static com.webcheckers.ui.WebServer.GAME_URL;
import static com.webcheckers.ui.WebServer.HOME_URL;

/**
 * @author Zeke Miller
 */
public class PostStartGameRoute implements Route {

    private static final String OPPONENT_PARAM = "opponent";

    @Override
    public Object handle( Request request, Response response ) {

        String name = request.session().attribute( PLAYER_NAME_ATTR );

        if ( name == null ) {
            response.redirect( HOME_URL );
            return null;
        }

        GameCenter gameCenter = GameCenter.getInstance();

        String opponentName = request.queryParams( OPPONENT_PARAM );

        if ( gameCenter.isPlayerInGame( name ) ||
             gameCenter.isPlayerInGame( opponentName ) ) {
            response.redirect( HOME_URL );
            return null;
        }

        if ( !gameCenter.addGame(name, opponentName) ) {
            response.redirect( HOME_URL );
            return null;
        }

        response.redirect( GAME_URL );
        return null;
    }
}
