package com.webcheckers.model;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/*
* Board class in the model tier
* */
public class Board implements Iterable<Row> {

    //7 rows
    private List<Row> rows;
    private Stack<Board> validBoards;

    /*
    * constructs new Board
    * */
    public Board(){
        validBoards = new Stack<>();
        this.rows = new ArrayList<>();
        for (int i = 0; i < 8; i++){
            rows.add(new Row(i));
        }
    }

    public void fillRedBoard(){
        for( Row row: rows){
            int index = row.getIndex();
            switch (index) {
                case 0:
                    row.fillWhiteRow();
                    break;
                case 1:
                    row.fillWhiteRow();
                    break;
                case 2:
                    row.fillWhiteRow();
                    break;
                case 3:
                    row.validateRow();
                    break;
                case 4:
                    row.validateRow();
                    break;
                case 5:
                    row.fillRedRow();
                    break;
                case 6:
                    row.fillRedRow();
                    break;
                case 7:
                    row.fillRedRow();
                    break;
            }
        }
    }

    public void fillWhiteBoard(){
        for( Row row: rows){
            switch (row.getIndex()){
                case 0:
                    row.fillRedRow();
                    break;
                case 1:
                    row.fillRedRow();
                    break;
                case 2:
                    row.fillRedRow();
                    break;
                case 3:
                    row.validateRow();
                    break;
                case 4:
                    row.validateRow();
                    break;
                case 5:
                    row.fillWhiteRow();
                    break;
                case 6:
                    row.fillWhiteRow();
                    break;
                case 7:
                    row.fillWhiteRow();
                    break;
            }
        }
    }

    public boolean spaceIsValid(int rowIndex, int spaceIndex){
        Row checkRow = rows.get(rowIndex);
        return checkRow.isSpaceValid(spaceIndex);
    }

    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }

    /**
     * adds a valid board to the stack
     * for use with BackupMove
     * @param b board to push
     */
    public void addValidBoard( Board b ) { validBoards.push(b); }

    public Board getLastValidBoard(){ return validBoards.pop(); }
}
