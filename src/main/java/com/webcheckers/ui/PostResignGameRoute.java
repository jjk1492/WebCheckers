package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.ErrorMessage;
import com.webcheckers.model.InfoMessage;
import spark.Request;
import spark.Response;
import spark.Route;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;
import static com.webcheckers.ui.WebServer.HOME_URL;

/**
 * PostResignGameRoute class of the ui tier
 */
public class PostResignGameRoute implements Route {

    private static final String GAME_RESIGN_ERROR =
            "Couldn't resign from the game, please try again!";
    private static final String GAME_RESIGN_INFO =
            "Resigned from game!";
    private GameCenter gameCenter;

    /**
     * constructor
     * @param gameCenter the GameCenter
     */
    public PostResignGameRoute(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String name = request.queryParams( PLAYER_NAME_ATTR );
        gameCenter.finishedGame(name);
        if(gameCenter.isPlayerInGame(name)){
            return new ErrorMessage(GAME_RESIGN_ERROR);
        }
        response.redirect(HOME_URL);
        return new InfoMessage(GAME_RESIGN_INFO);
    }
}
