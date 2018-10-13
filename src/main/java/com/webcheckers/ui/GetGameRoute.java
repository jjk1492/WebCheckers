package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

/**
 * The UI Controller to GET the Game page.
 *
 * @author Spencer Fleming
 */
public class GetGameRoute implements Route {

    private Renderer gameRenderer;

    /*
     * constructs new GetGameRoute
     * @param renderer GamePageRenderer
     * */
    public GetGameRoute(Renderer renderer){
        this.gameRenderer = renderer;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        //need to implement GamePageRenderer before I can write this

        //if you select player not in game: start game with you as starting (red)player
        //if you select a player already in a game: return to homepage with an error
        //if you are selected and you are not in a game: start a game with you as white player


        PlayerLobby lobby = PlayerLobby.getInstance();
        //also get instance of gamecenter in application tier
        Map<String, Object> map = new HashMap<>();

        String playerName = request.queryParams( "playerName" );
        String opponentName = request.queryParams("player"); //need to find clicked player

        Player redPlayer = lobby.getPlayer(playerName);
        Player whitePlayer = lobby.getPlayer(opponentName);
        //check if players are in a game already
        System.out.println("\n\n\n\n" + redPlayer.getPlayerName() + "\n\n\n\n"  );//whitePlayer.getPlayerName() + "\n\n\n\n");
        //use gamecenter to make a game

        map.put("currentPlayer", redPlayer);
        map.put("redPlayer", redPlayer);
        map.put("whitePlayer", whitePlayer);

        return gameRenderer.render(request.session(), map);
    }
}
