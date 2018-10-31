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
    	System.out.println( "PostCheckTurn invoked" );
        String result;
        String name = request.session().attribute( PLAYER_NAME_ATTR );

        if ( gameCenter.isPlayerActive( name ) ) {
            result = "true";
        }
        else {
            result = "false";
        }
        System.out.println( name + " checked and got: " + result );
        String json = new Gson().toJson( new InfoMessage( result ) );
        System.out.println( json );
  
        String other = "{\"type\":\"info\",\"message\":\"" + result + "\"}";
        System.out.println( other );

//        gameCenter.forceSwapTurn( name );
        return other;
        // TODO not sure exactly how the black box JS expects it?
//        return json;
    }
}
