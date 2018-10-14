package com.webcheckers.ui;

import spark.Session;

import java.util.Map;

/**
 * Renderer interface (created to follow dependency inversion)
 *
 * @author Zeke Miller
 */
public interface Renderer {

    /**
     * default renderer method, typically will call the other render method
     * with an empty or default value Map.  Session still required since the
     * information in a session is often required for rendering, even if it
     * is only a single value
     * @param session the spark session from the request
     * @return rendered page
     */
    Object render( Session session );

    /**
     * render method that can be passed a Map with information for the model.
     * Useful for filling in custom information that can't be retrieved from
     * the Session or other methods
     * @param session the spark session from the request
     * @param model a non-null Map with optional values
     * @return rendered page
     */
    Object render( Session session, Map< String, Object > model );

}
