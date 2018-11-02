package com.webcheckers.model;

import static com.webcheckers.model.Game.NUM_ROWS;

/*
 * Position class in the model tier
 * */
public class Position {

    private int row;
    private int cell;


    /*
     * constructs a Position object
     * @param row int from 0 to 7
     * @param cell int 0 to 7
     * */
    public Position(int row, int cell){
        this.row = row;
        this.cell = cell;
    }


    public Position getInverse() {
        return new Position( NUM_ROWS - 1 - row, cell );
    }

    /*
     * returns row
     * */
    public int getRow(){
        return row;
    }

    /*
     * returns cell
     * */
    public int getCell() {
        return cell;
    }

    @Override
    public String toString() {
        return String.format( "(%d,%d)", row, cell );
    }
}
