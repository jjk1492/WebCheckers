package com.webcheckers.ui;

import spark.Session;

import java.util.Map;

/**
 * @author Zeke Miller
 */
public interface Renderer {

    Object render( Session session );

    Object render( Session session, Map< String, Object > model );

}
