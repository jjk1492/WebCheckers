package com.webcheckers.model;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
* Board class in the model tier
* */
public class Board implements Iterable<Row> {

    //7 rows
    private List<Row> rows;

    /*
    * constructs new Board
    * */
    public Board(){
        this.rows = new ArrayList<>();
        for (int i = 0; i < 7; i++){
            rows.add(new Row(i));
        }
    }

    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }
}
