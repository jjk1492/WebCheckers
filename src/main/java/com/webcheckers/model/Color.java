package com.webcheckers.model;

public enum Color {
    RED, WHITE;

    /**
     * helper method for getOpposite()
     * @return RED if the color the function is performed on is WHITE, vice versa
     */
    public Color getOpposite() {
        return getOpposite( this );
    }

    /**
     * function literally used to return the opposing color
     * @param color the color to get the opposite of
     * @return RED if the color passed in is WHITE, vice versa
     */
    public static Color getOpposite( Color color ) {
        if ( color.equals(RED)) {
            return WHITE;
        }
        return RED;
    }
}
