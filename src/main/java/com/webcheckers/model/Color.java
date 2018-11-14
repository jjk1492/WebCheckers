package com.webcheckers.model;

public enum Color {
    RED(Direction.DOWN), WHITE(Direction.UP);

    private final Direction direction;

    Color( Direction direction ) {
        this.direction = direction;
    }

    public int getIncrement() {
        return direction.getIncrement();
    }

//    public Color getOpposite() {
//        return getOpposite( this );
//    }
//
//    public static Color getOpposite( Color color ) {
//        if ( color.equals(RED)) {
//            return WHITE;
//        }
//        return RED;
//    }

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
