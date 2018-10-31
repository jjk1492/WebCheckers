package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.InfoMessage;
import spark.Request;
import spark.Response;
import spark.Route;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;

public class PostCheckTurnRoute implements Route {

    private GameCenter gameCenter;

    public PostCheckTurnRoute( GameCenter gameCenter ) {
        this.gameCenter = gameCenter;
    }


    @Override
    public Object handle(Request request, Response response) throws Exception {
        String name = request.session().attribute( PLAYER_NAME_ATTR );
        String result = Boolean.toString( gameCenter.isPlayerActive( name ) );

//        gameCenter.forceSwapTurn( name );

        String json;
        json = new Gson().toJson( new InfoMessage( result ) );
  
//        String other = "{\"type\":\"info\",\"message\":\"" + result + "\"}";
//        System.out.println( other );
//        return other;
        // TODO not sure exactly how the black box JS expects it?
        return json;
    }
}
