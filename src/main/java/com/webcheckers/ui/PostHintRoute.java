package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.*;
import spark.Request;
import spark.Response;
import spark.Route;

import static com.webcheckers.model.Color.WHITE;
import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;

/**
 * PostHint route
 */
public class PostHintRoute implements Route {

    private static final String NO_VALID_MOVES = "Couldn't find a valid move!";

    private GameCenter gameCenter;

    /**
     * constructor for PostHintRoute
     * @param gameCenter
     */
    public PostHintRoute(GameCenter gameCenter){ this.gameCenter = gameCenter; }

    /**
     * sends a message if hints were displayed or not
     * @param request the spark request object
     * @param response he response spark object
     * @return json with message
     * @throws Exception
     */
    @Override
    public Object handle(Request request, Response response) throws Exception{

        String playerName = request.session().attribute( PLAYER_NAME_ATTR );
        Game currentGame = gameCenter.getGame(playerName);
        Move move = currentGame.getValidMove();

        Message message;
        if ( move == null ) {
            message = new ErrorMessage( NO_VALID_MOVES );
        }
        else {
            if ( currentGame.getActiveColor() == WHITE ) {
                move = move.getInverse();
            }
            message = new InfoMessage( "You can move from " + move.toString() );
        }

        String json;
        Gson gson = new GsonBuilder().create();
        json = gson.toJson(message);

        return json;
    }
}

