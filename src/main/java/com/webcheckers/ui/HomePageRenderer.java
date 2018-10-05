package com.webcheckers.ui;

import spark.ModelAndView;
import spark.Session;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;

/**
 * @author Zeke Miller
 */
public class HomePageRenderer implements Renderer {

    private static final String VIEW_NAME = "home.ftl";
    private static final String NO_NAME_STRING = "";

    private TemplateEngine templateEngine;

    public HomePageRenderer( TemplateEngine templateEngine ) {
        this.templateEngine = templateEngine;
    }

    public Object render( Session session ) {
        return render( session, new HashMap<>() );
    }

    public Object render( Session session, Map<String, Object> model ) {
        String playerName = session.attribute( PLAYER_NAME_ATTR );
        if ( playerName == null ) {
            playerName = NO_NAME_STRING;
        }
        model.put("title", "Welcome!");
        ModelAndView modelAndView = new ModelAndView( model, VIEW_NAME );
        return templateEngine.render( modelAndView );
    }

}
