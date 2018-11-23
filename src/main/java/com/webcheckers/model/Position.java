package com.webcheckers.model;

import static com.webcheckers.model.Board.COLS;
import static com.webcheckers.model.Board.ROWS;

/**
 * Position class in the model tier
 * @author Zeke Miller
 */
public class Position {

    private final int row;
    private final int cell;


    /**
     * constructs a Position object
     * @param row int from 0 to 7
     * @param cell int 0 to 7
     */
    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }


    /**
     * gets the corresponding position if the board were rotated 180 degrees
     * @return inverted Position
     */
    public Position getInverse()  {
        return new Position( ROWS - 1 - row, COLS - 1 - cell );
    }

    /**
     * @return row
     */
    public int getRow(){
        return row;
    }

    /**
     * @return cell
     */
    public int getCell() {
        return cell;
    }


    @Override
    public String toString() {
        return "(" + row + "," + cell + ")";
    }
}
