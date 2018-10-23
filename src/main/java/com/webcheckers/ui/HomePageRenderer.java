package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import spark.ModelAndView;
import spark.Session;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;
import static com.webcheckers.ui.SignInRenderer.MESSAGE_ATTR;
import static com.webcheckers.ui.SignInRenderer.MESSAGE_TYPE_ATTR;

/**
 * Renderer to keep behavior in one place since multiple places could render
 * the home page
 * @author Zeke Miller
 */
public class HomePageRenderer implements Renderer {

    // constants

    static final String TITLE_ATTR = "title";

    private static final String VIEW_NAME = "home.ftl";
    private static final String NO_NAME_STRING = "";
    private static final String SIGNED_IN_ATTR = "signedIn";
    private static final String PLAYER_LIST_ATTR = "players";
    private static final String DEFAULT_TITLE = "Welcome!";



    // fields

    private TemplateEngine templateEngine;


    // constructors

    /**
     * Default constructor, requires a template engine for dependency inversion
     * @param templateEngine the rendering engine
     */
    public HomePageRenderer( TemplateEngine templateEngine ) {
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
     * renders the home page using the player name attribute (if any) from
     * the session
     * @param session the spark session from the request
     * @param model a non-null Map with optional values
     * @return the rendered page
     */
    @Override
    public Object render( Session session, Map<String, Object> model ) {

        if ( model == null ) {
            model = new HashMap<>();
        }

        PlayerLobby lobby = PlayerLobby.getInstance();

        String name = session.attribute( PLAYER_NAME_ATTR );
        boolean signedIn = name != null;
        if ( !signedIn ) {
            name = NO_NAME_STRING;
        }

        model.put( SIGNED_IN_ATTR, signedIn );
        model.put( PLAYER_NAME_ATTR, name );
        model.put( PLAYER_LIST_ATTR, lobby.getAllPlayers() );
        model.put( TITLE_ATTR, DEFAULT_TITLE );

//        if ( !model.containsKey( MESSAGE_ATTR )
//             || !model.containsKey( MESSAGE_TYPE_ATTR ) ) {
//            model.put( MESSAGE_ATTR, "" );
//            model.put( MESSAGE_TYPE_ATTR, "" );
//        }

        ModelAndView modelAndView = new ModelAndView( model, VIEW_NAME );
        return templateEngine.render( modelAndView );
    }
}
