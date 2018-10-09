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
        return renderer.render(request.session());
    }
}
