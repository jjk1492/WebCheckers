package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.*;
import spark.Request;
import spark.Response;
import spark.Route;
import static com.webcheckers.ui.PostSignInRoute.PLAYER_NAME_ATTR;

/**
 * PostBackupMove route
 */
public class PostBackupMove implements Route{
    private static final String BACKUP_INFO ="Reset board to your last valid move";
    private static final String BACKUP_ERROR = "There was no move to backup to!";

    private GameCenter gameCenter;
    private Message message;

    /**
     * constructor for PostBackupMove
     * @param gameCenter
     */
    public PostBackupMove(GameCenter gameCenter){ this.gameCenter = gameCenter; }

    /**
     * sends a message if a backup move was successful or not
     * @param request the spark request object
     * @param response he response spark object
     * @return json with message
     * @throws Exception
     */
    @Override
    public Object handle(Request request, Response response) throws Exception{

        String playerName = request.session().attribute( PLAYER_NAME_ATTR );
        Game currentGame = gameCenter.getGame(playerName);
        Boolean moveLeft = currentGame.backupMove();

        if(moveLeft){
            message = new InfoMessage(BACKUP_INFO);
        }
        else{
            message = new ErrorMessage(BACKUP_ERROR);
        }

        String json;
        Gson gson = new GsonBuilder().create();
        json = gson.toJson( message );

        return json;
    }
}
