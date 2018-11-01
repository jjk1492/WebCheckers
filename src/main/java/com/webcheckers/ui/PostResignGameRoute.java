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

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;
import static com.webcheckers.ui.SignInRenderer.MESSAGE_ATTR;
import static com.webcheckers.ui.WebServer.HOME_URL;

/**
 * PostResignGameRoute class of the ui tier
 */
public class PostResignGameRoute implements Route {

    private static final String GAME_RESIGN_ERROR =
            "Couldn't resign from the game, please try again!";
    private static final String GAME_RESIGN_INFO =
            "Resigned from game, please redirect to the home page!";

    private GameCenter gameCenter;
    private Message message;

    /**
     * constructor
     * @param gameCenter the GameCenter
     */
    public PostResignGameRoute(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String name = request.session().attribute( PLAYER_NAME_ATTR );
        gameCenter.finishedGame(name);
        if(gameCenter.isPlayerInGame(name)){
            message = new ErrorMessage(GAME_RESIGN_ERROR);
        }else {
            message = new InfoMessage(GAME_RESIGN_INFO);
        }

        String json;
        Gson gson = new GsonBuilder().create();
        json = gson.toJson( message );

        return json;
    }
}