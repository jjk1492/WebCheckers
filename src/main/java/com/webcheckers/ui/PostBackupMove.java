package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.*;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Deque;
import java.util.Stack;

import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;


public class PostBackupMove implements Route{
    private static final String BACKUP_INFO ="Reset board to your last valid move";
    private static final String BACKUP_ERROR = "There was no move to backup to!";

    private GameCenter gameCenter;
    private Message message;

    public PostBackupMove(GameCenter gameCenter){ this.gameCenter = gameCenter; }

    @Override
    public Object handle(Request request, Response response) throws Exception{

        String playerName = request.session().attribute( PLAYER_NAME_ATTR );
        Game currentGame = gameCenter.getGame(playerName);
        Deque<Move> stack = currentGame.backupMove();

        if(stack.isEmpty()){
           message = new ErrorMessage(BACKUP_ERROR);
        }
        else{
            stack.pop();
            message = new InfoMessage(BACKUP_INFO);
        }

        String json;
        Gson gson = new GsonBuilder().create();
        json = gson.toJson( message );

        return json;
    }
}
