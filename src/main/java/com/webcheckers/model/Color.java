package com.webcheckers.model;

public enum Color {
    RED(Direction.UP), WHITE(Direction.DOWN);

    private final Direction direction;

    Color( Direction direction ) {
        this.direction = direction;
    }

    public int getIncrement() {
        return direction.getIncrement();
    }

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

    private enum Direction {
        UP(-1), DOWN(1);
        private final int increment;

        Direction( int increment ) {
            this.increment = increment;
        }

        public int getIncrement() {
            return increment;
        }
    }
}
