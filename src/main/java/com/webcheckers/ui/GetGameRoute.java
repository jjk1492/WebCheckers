package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * The UI Controller to GET the Game page.
 *
 * @author Spencer Fleming
 */
public class GetGameRoute implements Route {

    private Renderer renderer;

    /*
     * constructs new GetGameRoute
     * @param renderer GamePageRenderer
     * */
    public GetGameRoute(Renderer renderer){
        this.renderer = renderer;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        //need to implement GamePageRenderer before I can write this

        //if you select player not in game: start game with you as starting (red)player
        //if you select a player already in a game: return to homepage with an error
        //if you are selected and you are not in a game: start a game with you as white player

        return renderer.render(request.session());
    }
}
