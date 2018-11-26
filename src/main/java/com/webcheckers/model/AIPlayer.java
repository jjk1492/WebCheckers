package com.webcheckers.model;

public class AIPlayer extends Player {

    public AIPlayer(){
        super("ai_player");
    }

    public void takeTurn(Game game){

        Move move = game.getValidMove();

        while(move != null){
            game.tryMove(move);
            move = game.getValidMove();
        }

        game.applyTurn();
    }
}
