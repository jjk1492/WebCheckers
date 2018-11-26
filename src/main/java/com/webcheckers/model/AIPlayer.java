package com.webcheckers.model;

public class AIPlayer extends Player {

    public AIPlayer(){
        super("ai_player");
    }

    public void takeTurn(Game game){
        Move move;
        if (game.forceJump()) {
            while (game.forceJump()) {
                move = Move.aiMove(game); //will need to prevent endless loop with double jump
                game.tryMove(move);
            }
        }else{
            move = Move.aiMove(game);
            game.tryMove(move);
        }
        game.applyTurn();
    }
}
