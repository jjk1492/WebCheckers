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
}
