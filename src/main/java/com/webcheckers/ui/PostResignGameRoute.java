package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * PostResignGameRoute class of the ui tier
 */
public class PostResignGameRoute implements Route {

    private GameCenter gameCenter;

    /**
     * constructor
     * @param gameCenter the GameCenter
     */
    public PostResignGameRoute(GameCenter gameCenter){
        this.gameCenter = gameCenter;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}
