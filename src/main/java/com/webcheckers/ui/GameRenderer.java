package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.ModelAndView;
import spark.Session;
import spark.TemplateEngine;
import java.util.HashMap;
import java.util.Map;
import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;

public class GameRenderer implements com.webcheckers.ui.Renderer {

    enum ActiveColor{
        RED, WHITE
    }

    private ActiveColor activeColor = ActiveColor.RED;


    private static final String VIEW_NAME = "game.ftl";

    private static final String CURRENT_PLAYER_ATTR = "currentPlayer";
    private static final String VIEW_MODE_ATTR = "viewMode";
    private static final String RED_PLAYER_ATTR = "redPlayer";
    private static final String WHITE_PLAYER_ATTR = "whitePlayer";
    private static final String BOARD_ATTR = "board";
    private static final String MESSAGE_ATTR = "message";
    private static final String ACTIVE_COLOR_ATTR= "activeColor";

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

        String playerName = session.attribute(PLAYER_NAME_ATTR);
        GameCenter gameCenter = GameCenter.getInstance();

        Game currentGame = gameCenter.getGame(playerName);

        //send to game
        Player redPlayer = currentGame.getRedPlayer();
        Player whitePlayer = currentGame.getWhitePlayer();
        Player currentPlayer = currentGame.getCurrentPlayer();

        if( currentPlayer.equals(whitePlayer) ){
            activeColor = ActiveColor.WHITE;
        }

        model.put(VIEW_MODE_ATTR, "PLAY");
        model.put(RED_PLAYER_ATTR, redPlayer);
        model.put(WHITE_PLAYER_ATTR, whitePlayer);
        model.put(CURRENT_PLAYER_ATTR, currentPlayer);
        model.put(BOARD_ATTR, currentGame.getBoard());
        model.put(MESSAGE_ATTR, null);
        model.put(ACTIVE_COLOR_ATTR, activeColor);
        ModelAndView modelAndView = new ModelAndView( model, VIEW_NAME );
        return templateEngine.render( modelAndView );
    }
}
