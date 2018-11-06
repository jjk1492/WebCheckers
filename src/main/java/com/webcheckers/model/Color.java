package com.webcheckers.model;

public enum Color {
    RED, WHITE;

    public Color getOpposite() {
        return getOpposite( this );
    }

    public static Color getOpposite( Color color ) {
        if ( color.equals(RED)) {
            return WHITE;
        }
        return RED;
    }
}
