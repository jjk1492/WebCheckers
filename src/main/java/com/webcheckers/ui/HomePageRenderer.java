package com.webcheckers.ui;

import spark.ModelAndView;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zeke Miller
 */
public class HomePageRenderer implements Renderer {

    private static final String VIEW_NAME = "home.ftl";

    private TemplateEngine templateEngine;


    public HomePageRenderer( TemplateEngine templateEngine ) {
        this.templateEngine = templateEngine;
    }

    public Object render() {
        return render( new HashMap<>() );
    }

    public Object render( Map<String, Object> model ) {
        model.put("title", "Welcome!");
        ModelAndView modelAndView = new ModelAndView( model, VIEW_NAME );
        return templateEngine.render( modelAndView );
    }

}
