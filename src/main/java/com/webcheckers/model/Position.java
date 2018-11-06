package com.webcheckers.model;

import java.util.ArrayList;

/*
 * Position class in the model tier
 * */
public class Position {

    private int row;
    private int cell;


    /**
     * constructs a Position object
     * @param row int from 0 to 7
     * @param cell int 0 to 7
     */
    public Position(int row, int cell){
        this.row = row;
        this.cell = cell;

    }


    public Position getInverse() {
        ArrayList<Integer> inverseIndex = new ArrayList<>();
        inverseIndex.add(7);
        inverseIndex.add(6);
        inverseIndex.add(5);
        inverseIndex.add(4);
        inverseIndex.add(3);
        inverseIndex.add(2);
        inverseIndex.add(1);
        inverseIndex.add(0);
        return new Position( inverseIndex.get(row), inverseIndex.get(cell));
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

}
