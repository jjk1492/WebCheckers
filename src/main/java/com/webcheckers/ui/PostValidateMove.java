package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Move;
import spark.Request;
import spark.Response;
import spark.Route;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;

public class PostValidateMove implements Route {

    private GameCenter gameCenter;

    public PostValidateMove( GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {

        Gson gson = new Gson();
        Move move = gson.fromJson(request.body(), Move.class);

        String playerName = request.session().attribute( PLAYER_NAME_ATTR );
        Game currentGame = gameCenter.getGame(playerName);
        Message moveMessage = currentGame.validateMove(move);

        String json;
        json = gson.toJson(moveMessage);
        return json;
    }
}
