package com.webcheckers.model;

import java.util.Objects;

/**
 * class that represents a piece on the board
 * @author: Nick Sander
 */
public class Piece {
    public enum Type{
        SINGLE, KING;
    }

    public Color color;
    public Type type;

    /**
     * constructor for the piece
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
