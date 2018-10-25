package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;
import static com.webcheckers.ui.WebServer.HOME_URL;

/**
 * The UI Controller to GET the Sign out route, directing to the Home page.
 *
 * @author Nick Sander
 */
public class GetSignOutRoute
        implements Route {

    private static final Logger LOG = Logger
            .getLogger( GetSignOutRoute.class.getName() );

    private final Renderer renderer;
    private final GameCenter gameCenter;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /signout} HTTP request.
     *
     * @param renderer the HTML template rendering engine
     */
    public GetSignOutRoute( final Renderer renderer, GameCenter gameCenter ) {
        // validation
        Objects.requireNonNull( renderer, "renderer must not be null" );
        Objects.requireNonNull( gameCenter, "gameCenter must not be null" );

        this.renderer = renderer;
        this.gameCenter = gameCenter;

        LOG.config( "GetSignOutRoute is initialized." );
    }

    /**
     * Render the WebCheckers Home page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     *
     * @return the rendered HTML for the Home page
     */
    @Override
    public Object handle( Request request, Response response ) {

        LOG.finer( "GetSignOutRoute is invoked." );

        String playerName = request.session().attribute( PLAYER_NAME_ATTR );
        if ( playerName != null) {
            gameCenter.removePlayer(playerName);
            response.redirect( HOME_URL );
            return null;
        }
        else {
            return renderer.render( request.session());
        }
    }
}