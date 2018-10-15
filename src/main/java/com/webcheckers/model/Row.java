package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
* Row class in model tier
* */
public class Row implements Iterable<Space> {

    //7 spaces
    private List<Space> spaces;
    private int index;

    /*
    * constructs a new Row
    * @param index of row
    * */
    public Row(int index){
        spaces = new ArrayList<>();
        this.index = index;
        for (int i = 0; i < 7; i++){
            spaces.add(new Space(i));
        }

    }

    /*
    * returns row index
    * */
    public int getIndex() {
        return index;
    }

    @Override
    public Iterator<Space> iterator() {
        return spaces.iterator();
    }
}
