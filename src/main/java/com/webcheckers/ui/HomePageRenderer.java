package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.ModelAndView;
import spark.Session;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;
import static com.webcheckers.model.Color.RED;

/**
 * Renderer to keep behavior in one place since multiple places could render
 * the home page
 * @author Zeke Miller
 */
public class HomePageRenderer implements Renderer {

    // constants

    private static final String VIEW_NAME = "home.ftl";
    private static final String NO_NAME_STRING = "";
    private static final String SIGNED_IN_ATTR = "signedIn";
    private static final String PLAYER_LIST_ATTR = "players";
    private static final String TITLE_ATTR = "title";
    private static final String DEFAULT_TITLE = "Welcome!";


    // Game View Constant
    private static final String VIEW_NAME_GAME = "game.ftl";
    private static final String CURRENT_PLAYER_ATTR = "currentPlayer";
    private static final String VIEW_MODE_ATTR = "viewMode";
    private static final String RED_PLAYER_ATTR = "redPlayer";
    private static final String WHITE_PLAYER_ATTR = "whitePlayer";
    private static final String BOARD_ATTR = "board";
    private static final String MESSAGE_ATTR = "message";
    private static final String ACTIVE_COLOR_ATTR= "activeColor";


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
        GameCenter gameCenter = GameCenter.getInstance();

        Game currentGame = gameCenter.getGame(name);

        if( currentGame != null){

            Player redPlayer = currentGame.getRedPlayer();
            Player whitePlayer = currentGame.getWhitePlayer();

            model.put(TITLE_ATTR, redPlayer.getName() + " vs. " + whitePlayer.getName());
            model.put(VIEW_MODE_ATTR, "PLAY");
            model.put(RED_PLAYER_ATTR, redPlayer);
            model.put(WHITE_PLAYER_ATTR, whitePlayer);
            model.put(CURRENT_PLAYER_ATTR, whitePlayer);
            model.put(BOARD_ATTR, currentGame.getWhiteBoard());
            model.put(MESSAGE_ATTR, null);
            model.put(ACTIVE_COLOR_ATTR, RED);
            ModelAndView modelAndView = new ModelAndView( model, VIEW_NAME_GAME );
            return templateEngine.render( modelAndView );
        }

        model.put( SIGNED_IN_ATTR, signedIn );
        model.put( PLAYER_NAME_ATTR, name );
        model.put( PLAYER_LIST_ATTR, lobby.getAllPlayers() );
        model.put( TITLE_ATTR, DEFAULT_TITLE );
        model.put( MESSAGE_ATTR, null);

        ModelAndView modelAndView = new ModelAndView( model, VIEW_NAME );
        return templateEngine.render( modelAndView );
    }
}
