package com.webcheckers.ui;

import spark.*;

public class PostSignInRoute implements Route {

    private static final String PLAYER_NAME_ATTR = "playerName";


    private Renderer homeRenderer;
    private Renderer signInRenderer;

    public PostSignInRoute( Renderer homeRenderer, Renderer signInRenderer ) {
        this.homeRenderer = homeRenderer;
        this.signInRenderer = signInRenderer;
    }

    @Override
    public Object handle( Request request, Response response ) throws Exception {

        String playerName = request.queryParams( PLAYER_NAME_ATTR );
        System.out.println( playerName );

        // TODO check name validity for real
        boolean nameValid = playerName != null;
        if ( nameValid ) {
            return homeRenderer.render();
        }
        else {
            return signInRenderer.render();
        }
    }
}
