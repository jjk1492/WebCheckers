package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.ErrorMessage;
import com.webcheckers.model.InfoMessage;
import com.webcheckers.model.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;

/**
 * Handles POST /checkTurn to see if it is the player's turn
 *
 * @author Zeke Miller
 */
public class PostCheckTurnRoute implements Route {

    private static final String OPPONENT_RESIGNED_ERROR = "Your opponent resigned, please redirect to home!";
    private GameCenter gameCenter;

    /**
     * Constructor with a gameCenter for tracking games
     * @param gameCenter the gameCenter to use for logic
     */
    public PostCheckTurnRoute( GameCenter gameCenter ) {

        Objects.requireNonNull( gameCenter );
        this.gameCenter = gameCenter;
    }


    /**
     * Handles the HTTP request
     * @param request the Spark request object
     * @param response the Spark response object
     * @return JSonized InfoMessage object with if it's the player's turn or not
     * @throws Exception shouldn't throw any exceptions
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        String name = request.session().attribute( PLAYER_NAME_ATTR );
        String result = Boolean.toString( gameCenter.isPlayerActive( name ) );
        Message message;

        if (!gameCenter.isPlayerInGame(name)){
            message = new InfoMessage(OPPONENT_RESIGNED_ERROR);
        }
        else {
            message = new InfoMessage(result);
        }

        String json;
        Gson gson = new GsonBuilder().create();
        json = gson.toJson( message );

        return json;
    }
}
