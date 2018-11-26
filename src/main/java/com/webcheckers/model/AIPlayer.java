package com.webcheckers.model;

public class AIPlayer extends Player {

    public AIPlayer(){
        super("ai_player");
    }

    public void takeTurn(Game game){

        /* need game.getValidMove() method from hint branch
        Move move = game.getValidMove();
        game.tryMove(move);
        */

        game.tryMove(new Move(     //hard coded move for testing ai move
                new Position(2,3),
                new Position(3,4)
        ));

        game.applyTurn();
    }
}
