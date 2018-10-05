package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;

public class GetSignInRoute implements Route {

    private Renderer renderer;

    public GetSignInRoute( Renderer renderer ) {
        this.renderer = renderer;
    }

    @Override
    public Object handle( Request request, Response response ) throws Exception {
        return renderer.render();
    }
}