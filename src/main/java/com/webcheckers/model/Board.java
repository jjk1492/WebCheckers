package com.webcheckers.model;

import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board implements Iterable<Row> {

    private List<Row> rows;

    public Board(){
        this.rows = new ArrayList<>();
    }

    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }
}
