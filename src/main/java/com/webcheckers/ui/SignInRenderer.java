package com.webcheckers.ui;

import spark.ModelAndView;
import spark.Session;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

/**
 * Sign In page rendered to keep behavior in one place for rendering the page
 * Notably, more than one route uses this renderer so it is actually useful
 * @author Zeke Miller
 */
public class SignInRenderer implements Renderer {

    // private constants

    private static final String VIEW_NAME = "signin.ftl";


    // package private constants

    static final String MESSAGE_ATTR = "message";

    // fields

    private TemplateEngine templateEngine;


    // constructors

    /**
     * Default constructor, requires a template engine for dependency inversion
     * @param templateEngine the rendering engine
     */
    public SignInRenderer( TemplateEngine templateEngine ) {
        this.templateEngine = templateEngine;
    }


    // methods

    /**
     * default render method, simply calls other render method with a new Map
     * @param session the spark session from the request
     * @return rendered page
     */
    @Override
    public Object render( Session session ) {
        return render( session, new HashMap<>() );
    }


    /**
     * renders the sign in page
     * @param session the spark session from the request
     * @param model a non-null Map with optional values
     * @return the rendered page
     */
    @Override
    public Object render( Session session, Map<String, Object> model ) {

        if ( model == null ) {
            model = new HashMap<>();
        }

        // ensure renderer doesn't crash
//        if ( !model.containsKey( MESSAGE_ATTR )
//             || !model.containsKey( MESSAGE_TYPE_ATTR ) ) {
//            model.put( MESSAGE_ATTR, "" );
//            model.put( MESSAGE_TYPE_ATTR, "" );
//        }

        ModelAndView modelAndView = new ModelAndView( model, VIEW_NAME );
        return templateEngine.render( modelAndView );
    }
}
