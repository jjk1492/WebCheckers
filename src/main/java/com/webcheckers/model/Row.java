package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Row implements Iterable<Space> {

    private List<Space> spaces;
    private int index;

    public Row(int index){
        spaces = new ArrayList<>();
        this.index = index;
        for (int i = 0; i < 7; i++){
            spaces.add(new Space(i));
        }

    }

    public int getIndex() {
        return index;
    }

    @Override
    public Iterator<Space> iterator() {
        return spaces.iterator();
    }
}
