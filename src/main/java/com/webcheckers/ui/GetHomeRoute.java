package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute
        implements Route {

    private static final Logger LOG = Logger
            .getLogger( GetHomeRoute.class.getName() );

    private final Renderer renderer;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param renderer the HTML template rendering engine
     */
    public GetHomeRoute( final Renderer renderer ) {
        // validation
        Objects.requireNonNull( renderer, "templateEngine must not be null" );
        //
        this.renderer = renderer;
        //
        LOG.config( "GetHomeRoute is initialized." );
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

        LOG.finer( "GetHomeRoute is invoked." );
        // TODO timeout maybe?
        return renderer.render( request.session() );
    }

}