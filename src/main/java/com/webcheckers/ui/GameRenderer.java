package com.webcheckers.ui;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.ModelAndView;
import spark.Session;
import spark.TemplateEngine;
import java.util.HashMap;
import java.util.Map;

public class GameRenderer implements com.webcheckers.ui.Renderer {



    private static final String VIEW_NAME = "game.ftl";

    private static final String CURRENT_PLAYER_ATTR = "currentPlayer";
    private static final String VIEW_MODE_ATTR = "viewMode";
    private static final String RED_PLAYER_ATTR = "redPlayer";
    private static final String WHITE_PLAYER_ATTR = "whitePlayer";
    private static final String BOARD_ATTR = "board";

    private TemplateEngine templateEngine;

    public GameRenderer(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public Object render(Session session) {
        return render( session, new HashMap<>() );
    }

    @Override
    public Object render(Session session, Map<String, Object> model) {

        Player currentPlayer = session.attribute(CURRENT_PLAYER_ATTR);
        Game currentGame = session.attribute(BOARD_ATTR);

        //send to game
        model.put(VIEW_MODE_ATTR, "PLAY");
        model.put(RED_PLAYER_ATTR, currentGame.getRedPlayer());
        model.put(WHITE_PLAYER_ATTR, currentGame.getWhitePlayer());
        model.put(CURRENT_PLAYER_ATTR, currentGame.getCurrentPlayer());
        ModelAndView modelAndView = new ModelAndView( model, VIEW_NAME );
        return templateEngine.render( modelAndView );
    }
}
