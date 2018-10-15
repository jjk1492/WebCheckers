package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;
import static com.webcheckers.ui.WebServer.HOME_URL;

/**
 * Route handler to get the Sign In page
 *
 * @author Zeke Miller
 */
public class GetSignInRoute implements Route {

    // fields

    private Renderer renderer;


    // constructors

    /**
     * Normal constructor, requires a Renderer to render pages
     * @param renderer the renderer for the Route
     */
    public GetSignInRoute( Renderer renderer ) {
        this.renderer = renderer;
    }


    // methods

    /**
     * Handles GET /signin
     * @param request the HTTP request spark object
     * @param response the response spark object
     * @return the rendered page from the renderer
     * @throws Exception only if something crashes (hopefully won't happen)
     */
    @Override
    public Object handle( Request request, Response response ) throws Exception {
        if ( request.session().attribute( PLAYER_NAME_ATTR ) != null ) {
            response.redirect( HOME_URL );
            return null;
        }
        return renderer.render( request.session() );
    }
}