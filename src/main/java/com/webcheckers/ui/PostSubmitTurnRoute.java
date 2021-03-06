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
    private static final String FORCE_JUMP = "You still have a jump you need to make!";
    private GameCenter gameCenter;

    public PostSubmitTurnRoute(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String name = request.session().attribute( PLAYER_NAME_ATTR );
        Gson gson = new Gson();
        Message message;

        if(gameCenter.isPlayerInGame(name)){

            boolean success = gameCenter.finishTurn( name );

            if(!success){
                message = new ErrorMessage(FORCE_JUMP);
            }

            else if (gameCenter.isPlayerActive(name)) {
                message = new ErrorMessage(SWAP_TURN_ERROR);
            }
            else {
                message = new InfoMessage(SUBMIT_TURN_INFO);
                if (gameCenter.getOpponent(name).equals("ai_player")){
                    gameCenter.makeAIMoves(name);

                }
            }
        }
        else {
            message = new InfoMessage(GAME_OVER_INFO);
        }
        String json;
        json = gson.toJson(message);
        return json;
    }
}
