package com.webcheckers.model;

/**
 * class that represents a piece on the board
 * @author: Nick Sander
 */
public class Piece {
    enum Type{
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
}
