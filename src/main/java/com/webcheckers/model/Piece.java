package com.webcheckers.model;

public class Piece {
    enum Color{
        RED, WHITE;
    }

    enum Type{
        SINGLE, KING;
    }

    public Color color;
    public Type type;

    public Color getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }
}
