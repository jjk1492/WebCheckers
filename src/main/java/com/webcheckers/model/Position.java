package com.webcheckers.model;

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
}
