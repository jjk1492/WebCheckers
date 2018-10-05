package com.webcheckers.ui;

import spark.ModelAndView;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zeke Miller
 */
public class SignInRenderer implements Renderer {

    private static final String VIEW_NAME = "signin.ftl";

    static final String MESSAGE_TYPE_ATTR = "messageType";
    static final String MESSAGE_ATTR = "message";
    static final String MESSAGE_TYPE_ERROR = "error";
    static final String MESSAGE_TYPE_INFO = "info";

    private TemplateEngine templateEngine;


    public SignInRenderer( TemplateEngine templateEngine ) {
        this.templateEngine = templateEngine;
    }

    public Object render() {
        return render( new HashMap<>() );
    }

    public Object render( Map<String, Object> model ) {

        if ( !model.containsKey( MESSAGE_ATTR ) ) {
            model.put( MESSAGE_ATTR, "" );
            model.put( MESSAGE_TYPE_ATTR, "" );
        }
        ModelAndView modelAndView = new ModelAndView( model, VIEW_NAME );
        return templateEngine.render( modelAndView );
    }
}
