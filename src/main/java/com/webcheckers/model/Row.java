package com.webcheckers.model;

import java.util.*;

/**
* Row class in model tier
* */
public class Row implements Iterable<Space> {

    private List<Space> spaces;
    private int index;

    /**
      * constructs a new Row
      * @param index of row
      * @param spaces the list of spaces to store
      */
    public Row(int index, List<Space> spaces ){
        this.spaces = spaces;
        this.index = index;
    }

    /**
    * @return row index
    * */
    public int getIndex() {
        return index;
    }

    @Override
    public Iterator<Space> iterator() {
        return spaces.iterator();
    }
}
