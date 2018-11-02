package com.webcheckers.model;

import java.util.Objects;

/**
 * class that represents a piece on the board
 * @author Nick Sander
 */
public class Piece {
    public enum Type{
        SINGLE, KING
    }

    public Color color;
    public Type type;

    /**
     * constructor for a default piece, created with Type SINGLE since default pieces are not kings
     * @param color - an enumeration for the color of the piece, red or white
     */
    public Piece(Color color){
        this.color = color;
        this.type = Type.SINGLE;
    }

    /**
     * an additional constructor for the piece to be used for kings
     * @param color - an enumeration for the color of the piece, red or white
     * @param type - type is 'single' unless piece has made it to opposing edge of board
     */
    public Piece( Color color, Type type){
        this.color = color;
        this.type = type;
    }


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

    public boolean isValidStep( Move move ) {
        int startRow = move.getStart().getRow();
        int endRow = move.getEnd().getRow();
        return move.isStep() &&  ( startRow - endRow == 1 );
    }

    public boolean isValidJump( Move move ) {
        int startCol = move.getStart().getCell();
        int endCol = move.getEnd().getCell();
        return move.isJump() && ( Math.abs( startCol - endCol ) == 2 );
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Piece piece = (Piece) other;
        return color == piece.color &&
                type == piece.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, type);
    }
}
