package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.ModelAndView;
import spark.Session;
import spark.TemplateEngine;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.webcheckers.model.Color.RED;
import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;

public class GameRenderer implements Renderer {

    private static final String TEMPLATE_ENGINE_ERROR =
            "templateEngine must not be null";
    private static final String GAME_CENTER_ERROR =
            "gameCenter must not be null";

    // Game View Constant
    static final String GAME_VIEW_NAME = "game.ftl";
    static final String CURRENT_PLAYER_ATTR = "currentPlayer";
    static final String VIEW_MODE_ATTR = "viewMode";
    static final String VIEW_MODE_PLAY = "PLAY";
    static final String RED_PLAYER_ATTR = "redPlayer";
    static final String WHITE_PLAYER_ATTR = "whitePlayer";
    static final String BOARD_ATTR = "board";
    static final String ACTIVE_COLOR_ATTR= "activeColor";


    private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;


    public GameRenderer(TemplateEngine templateEngine, GameCenter gameCenter ) {
        Objects.requireNonNull( templateEngine, TEMPLATE_ENGINE_ERROR );
        Objects.requireNonNull( gameCenter, GAME_CENTER_ERROR );
        this.templateEngine = templateEngine;
        this.gameCenter = gameCenter;
    }


    @Override
    public Object render(Session session) {
        return render( session, new HashMap<>() );
    }



    @Override
    public Object render(Session session, Map<String, Object> model) {

        if( model == null ){
            model = new HashMap<>();
        }

        String name = session.attribute(PLAYER_NAME_ATTR);

        Game currentGame = gameCenter.getGame(name);

        //send to game
        Player redPlayer = currentGame.getRedPlayer();
        Player whitePlayer = currentGame.getWhitePlayer();

        model.put(RED_PLAYER_ATTR, redPlayer);
        model.put(WHITE_PLAYER_ATTR, whitePlayer);
        model.put(CURRENT_PLAYER_ATTR, redPlayer);
        model.put(BOARD_ATTR, currentGame.getRedBoard());
        model.put(ACTIVE_COLOR_ATTR, RED);
        ModelAndView modelAndView = new ModelAndView( model, GAME_VIEW_NAME );
        return templateEngine.render( modelAndView );
    }
}
