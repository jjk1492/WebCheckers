package com.webcheckers.model;

import java.util.Objects;

/**
 * @author Zeke Miller
 */
public abstract class Piece {

    public enum Type {
        SINGLE, KING
    }


    /**
     * Tells the current state of a piece:
     * BLOCKED - All surrounding spaces are occupied so the piece is unable to move
     * JUMP - The piece is able to make a jump, and therefore a jump should be made
     * OPEN - The piece is able to make at least 1 valid non-jump move
     */
    public enum State{
        BLOCKED, JUMP, OPEN
    }

    private final Color color;
    private final Type type;
    private State currentState;


    /**
     * constructor for a default piece, created with Type SINGLE since default pieces are not kings
     * @param color - an enumeration for the color of the piece, red or white
     */
    protected Piece( Type type, Color color, State state){
        this.color = color;
        this.currentState = state;
        this.type = type;
    }

    /**
     * make a copy of the piece
     * @return copied Piece
     */
    abstract Piece copy();

    /**
     * checks if the piece can take the given Move as a step
     * @param move the move to check
     * @return true if can take
     */
    abstract boolean isValidStep( Move move );

    /**
     * checks if the piece can take the given Move as a jump
     * @param move the move to check
     * @return true if can take
     */
    abstract boolean isValidJump( Move move );


    /**
     * getter for the color of the piece
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * getter for the type of the piece
     * @return type
     */
    public Type getType() {
        return type;
    }

    public State getState(){
        return currentState;
    }

    public void setState(State state){
        this.currentState = state;
    }


    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) return false;
        Piece piece = (Piece) other;
        return color == piece.color && type == piece.type && currentState == piece.currentState;
    }

    @Override
    public int hashCode() {
        return Objects.hash( color, type);
    }
}
