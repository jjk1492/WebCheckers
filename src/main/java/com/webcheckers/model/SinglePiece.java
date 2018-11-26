package com.webcheckers.model;

import static com.webcheckers.model.Piece.State.OPEN;
import static com.webcheckers.model.Piece.Type.SINGLE;


/**
 * class that represents a piece on the board
 * @author Zeke Miller
 * @author Nick Sander
 */
public class SinglePiece extends Piece {

//    public SinglePiece( Color color ) {
//        this( color, OPEN );
//    }


    public SinglePiece( Color color, State state ) {
        super( SINGLE, color, state );
    }


    /**
     * make a copy of the piece
     *
     * @return copied Piece
     */
    @Override
    Piece copy() {
        return new SinglePiece( getColor(), getState() );
    }


    /**
     * checks if the piece can take the given Move as a step
     * @param move the move to check
     * @return true if can take
     */
    @Override
    public boolean isValidStep( Move move ) {
        int startRow = move.getStart().getRow();
        int endRow = move.getEnd().getRow();
        return move.isStep() && ( endRow - startRow == getColor().getIncrement() );
    }


    /**
     * checks if the piece can take the given Move as a jump
     * @param move the move to check
     * @return true if can take
     */
    @Override
    public boolean isValidJump( Move move ) {
        int startRow = move.getStart().getRow();
        int endRow = move.getEnd().getRow();
        return move.isJump() && ( endRow - startRow == getColor().getIncrement() * 2 );
    }
}
