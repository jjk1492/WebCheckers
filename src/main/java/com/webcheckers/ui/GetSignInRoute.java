package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;
import static com.webcheckers.ui.WebServer.HOME_URL;

public class GetSignInRoute implements Route {

    private Renderer renderer;

    public GetSignInRoute( Renderer renderer ) {
        this.renderer = renderer;
    }

    @Override
    public Object handle( Request request, Response response ) throws Exception {
        if ( request.session().attribute( PLAYER_NAME_ATTR ) != null ) {
            response.redirect( HOME_URL );
            return null;
        }
        return renderer.render( request.session() );
    }
}