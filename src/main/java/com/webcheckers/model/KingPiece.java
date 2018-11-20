package com.webcheckers.model;

import static com.webcheckers.model.Piece.State.OPEN;
import static com.webcheckers.model.Piece.Type.KING;

/**
 * Represents a Kinged Piece
 *
 * @author Zeke Miller
 */
public class KingPiece extends Piece {


    public KingPiece( Color color ) {
        this( color, OPEN );
    }


    public KingPiece( Color color, State state ) {
        super( KING, color, state );
    }

    public KingPiece( Piece piece ) {
        super( KING, piece.getColor(), piece.getState() );
    }


    /**
     * make a copy of the piece
     *
     * @return copied Piece
     */
    @Override
    Piece copy() {
        return new KingPiece( getColor(), getState() );
    }


    /**
     * checks if the piece can take the given Move as a step
     *
     * @param move the move to check
     *
     * @return true if can take
     */
    @Override
    boolean isValidStep( Move move ) {
        return move.isStep();
    }


    /**
     * checks if the piece can take the given Move as a jump
     *
     * @param move the move to check
     *
     * @return true if can take
     */
    @Override
    boolean isValidJump( Move move ) {
        return move.isJump();
    }
}
