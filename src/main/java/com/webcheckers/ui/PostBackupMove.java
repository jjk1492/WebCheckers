package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.*;
import spark.Request;
import spark.Response;
import spark.Route;




public class PostBackupMove implements Route{
    private static final String BACKUP_INFO ="Reset board to your last move";
    private static final String BACKUP_ERROR = "Backup was unsuccessful :[";

    private GameCenter gameCenter;
    private Message message;

    public PostBackupMove(GameCenter gameCenter){ this.gameCenter = gameCenter; }

    @Override
    public Object handle(Request request, Response response) throws Exception{

        return message;
    }
}
