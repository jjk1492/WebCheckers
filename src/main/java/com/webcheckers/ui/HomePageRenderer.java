package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import spark.ModelAndView;
import spark.Session;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;

/**
 * Renderer to keep behavior in one place since multiple places could render
 * the home page
 * @author Zeke Miller
 */
public class HomePageRenderer implements Renderer {

    // constants

    static final String TITLE_ATTR = "title";
    static final String SIGNED_IN_ATTR = "signedIn";
    static final String PLAYER_LIST_ATTR = "players";
    static final String HOME_VIEW_NAME = "home.ftl";

    private static final String DEFAULT_TITLE = "Welcome!";

    private static final String TEMPLATE_ENGINE_ERROR =
            "templateEngine must not be null";
    private static final String PLAYER_LOBBY_ERROR =
            "playerLobby must not be null";
    private static final String NULL_SESSION_ERROR = "Session must not be null";


    // fields

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;


    // constructors

    /**
     * Default constructor, requires a template engine for dependency inversion
     * @param templateEngine the rendering engine
     */
    public HomePageRenderer( TemplateEngine templateEngine, PlayerLobby playerLobby ) {

        Objects.requireNonNull( templateEngine, TEMPLATE_ENGINE_ERROR );
        Objects.requireNonNull( playerLobby, PLAYER_LOBBY_ERROR );
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
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
     * @param model a Map with optional values
     * @return the rendered page
     */
    @Override
    public Object render( Session session, Map<String, Object> model ) {

        Objects.requireNonNull( session, NULL_SESSION_ERROR );

        if ( model == null ) {
            model = new HashMap<>();
        }

        String name = session.attribute( PLAYER_NAME_ATTR );
        boolean signedIn = name != null &&
                           playerLobby.getPlayer( name ) != null;

        model.put( SIGNED_IN_ATTR, signedIn );

        if ( playerLobby.getAllPlayers() != null ) {
            model.put( PLAYER_LIST_ATTR, playerLobby.getAllPlayers() );
        }

        if ( signedIn ) {
            model.put( PLAYER_NAME_ATTR, name );
        }

        if ( !model.containsKey( TITLE_ATTR ) ) {
            model.put( TITLE_ATTR, DEFAULT_TITLE );
        }

        ModelAndView modelAndView = new ModelAndView( model, HOME_VIEW_NAME );
        return templateEngine.render( modelAndView );
    }
}
