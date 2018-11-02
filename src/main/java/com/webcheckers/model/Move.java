package com.webcheckers.model;

/*
 * Move class in the model tier
 * */
public class Move {

    private Position start;
    private Position end;

    /*
     * constructs Move object
     * @param start initial Position
     * @param end final Position
     * */
    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
    }

    public Move getInverse() {
        return new Move( end.getInverse(), start.getInverse() );
    }

    /*
     * returns start Position
     * */
    public Position getStart() {
        return start;
    }

    /*
     * returns end Position
     * */
    public Position getEnd() {
        return end;
    }

    public boolean isStep() {
        int startRow = getStart().getRow();
        int startCol = getStart().getCell();
        int endRow = getEnd().getRow();
        int endCol = getEnd().getCell();
        if ( Math.abs( startRow - endRow ) != 1 ) {
            return false;
        }
        if ( Math.abs( startCol - endCol ) != 1 ) {
            return false;
        }
        return true;
    }

    public boolean isJump() {
        int startRow = getStart().getRow();
        int startCol = getStart().getCell();
        int endRow = getEnd().getRow();
        int endCol = getEnd().getCell();
        if ( Math.abs( startRow - endRow ) != 2 ) {
            return false;
        }
        if ( Math.abs( startCol - endCol ) != 2 ) {
            return false;
        }
        return true;
    }


}
