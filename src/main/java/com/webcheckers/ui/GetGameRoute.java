package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.HomePageRenderer.TITLE_ATTR;
import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;
import static com.webcheckers.ui.WebServer.HOME_URL;

/**
 * The UI Controller to GET the Game page.
 *
 * @author Spencer Fleming
 */
public class GetGameRoute implements Route {

    private static final String VS_FORMAT = "%s vs. %s";

    private final Renderer gameRenderer;
    private final GameCenter gameCenter;

    /**
     * constructs new GetGameRoute
     * @param renderer GamePageRenderer
     */
    public GetGameRoute(Renderer renderer, GameCenter gameCenter ){
        this.gameRenderer = renderer;
        this.gameCenter = gameCenter;

    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        String name = request.session().attribute( PLAYER_NAME_ATTR );
        if ( name == null ) {
            response.redirect( HOME_URL );
            return null;
        }

        if ( !gameCenter.isPlayerInGame( name ) ) {
            response.redirect( HOME_URL );
        }
        Map<String, Object> map = new HashMap<>();
        String opponentName = gameCenter.getOpponent( name );
        if ( opponentName == null ) {
            response.redirect( HOME_URL );
            return null;
        }

        map.put( TITLE_ATTR, String.format( VS_FORMAT, name, opponentName ) );
        return gameRenderer.render(request.session(), map);
    }
}
