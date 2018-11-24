package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.ErrorMessage;
import com.webcheckers.model.Game;
import com.webcheckers.model.InfoMessage;
import com.webcheckers.model.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;

/**
 * PostHint route
 */
public class PostHintRoute implements Route {
    private static final String HINT_INFO ="Hints have been displayed.";
    private static final String HINT_ERROR = "You can't display any hints right now.";

    private GameCenter gameCenter;
    private Message message;

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
        Boolean jumpPossible = currentGame.forceJump();

        if(jumpPossible){ //hints displayed
            message = new InfoMessage(HINT_INFO);
        }
        else{
            message = new ErrorMessage(HINT_ERROR);
        }

        String json;
        Gson gson = new GsonBuilder().create();
        json = gson.toJson( message );

        return json;
    }
}

