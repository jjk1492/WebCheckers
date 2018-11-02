package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.ErrorMessage;
import com.webcheckers.model.Game;
import com.webcheckers.model.InfoMessage;
import com.webcheckers.model.Message;
import org.omg.PortableInterceptor.ACTIVE;
import spark.Request;
import spark.Response;
import spark.Route;

import static com.webcheckers.ui.GameRenderer.ACTIVE_COLOR_ATTR;
import static com.webcheckers.ui.GameRenderer.BOARD_ATTR;
import static com.webcheckers.ui.GameRenderer.CURRENT_PLAYER_ATTR;
import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;
import static com.webcheckers.ui.WebServer.GAME_URL;

public class PostSubmitTurnRoute implements Route {

    private static final String SWAP_TURN_ERROR = "Could not submit turn, please try again!";
    private static final String SUBMIT_TURN_INFO = "Turn submitted!";
    private static final String GAME_OVER_INFO = "The game has ended, please redirect to home!";
    private GameCenter gameCenter;

    public PostSubmitTurnRoute(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String name = request.session().attribute( PLAYER_NAME_ATTR );
        Gson gson = new Gson();
        Message message;
        Game game;

        if(gameCenter.isPlayerInGame(name)){
            game = gameCenter.getGame(name);
            gameCenter.forceSwapTurn(name);
            if (gameCenter.isPlayerActive(name)) {
                message = new ErrorMessage(SWAP_TURN_ERROR);
            }
            else {
                // TODO actually submit all of the moves to the game
                message = game.submitTurn();
            }
        }
        else {
            // TODO send a message telling whether the player won or lost
            message = new InfoMessage(GAME_OVER_INFO);
        }
        String json;
        json = gson.toJson(message);
        return json;
    }
}
