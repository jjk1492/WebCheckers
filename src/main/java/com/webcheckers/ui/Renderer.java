package com.webcheckers.ui;

import java.util.Map;

/**
 * @author Zeke Miller
 */
public interface Renderer {

    Object render();

    Object render( Map< String, Object > model );

}
