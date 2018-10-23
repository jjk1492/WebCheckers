package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
* Row class in model tier
* */
public class Row implements Iterable<Space> {

    //7 spaces
    private List<Space> spaces;
    private int index;

    /**
    * constructs a new Row
    * @param index of row
    * */
    public Row(int index){
        spaces = new ArrayList<>();
        this.index = index;
        for (int i = 0; i < 8; i++){
            Space newSpace = new Space(i);
            spaces.add(newSpace);
        }

    }

    /**
     * Called at the beginning of a game to add red pieces to a row
     */
    public void fillRedRow(){
        for( Space space : spaces){
            if( index%2 == 0){
                if( space.getCellIdx()%2 == 1){
                    space.setPiece( new Piece(Color.RED, Piece.Type.SINGLE));
                }
            }
            else {
                if( space.getCellIdx()%2 == 0){
                    space.setPiece( new Piece(Color.RED, Piece.Type.SINGLE));
                }
            }
        }
    }

    /**
     * Called at the beginning of a game to add white pieces to a row
     */
    public void fillWhiteRow(){
        for( Space space : spaces){
            if( index%2 == 0){
                if( space.getCellIdx()%2 == 1){
                    space.setPiece( new Piece(Color.WHITE, Piece.Type.SINGLE));
                }
            }
            else {
                if( space.getCellIdx()%2 == 0){
                    space.setPiece( new Piece(Color.WHITE, Piece.Type.SINGLE));
                }
            }
        }
    }

    /**
     * Called at the beginning of a game to validate any space in a row that should be
     * able to have a piece dropped on it
     */
    public void validateRow(){
        for( Space space : spaces){
            if( index%2 == 0){
                if( space.getCellIdx()%2 == 1){
                    space.setValid(true);
                }
            }
            else{
                if( space.getCellIdx()%2 == 0){
                    space.setValid(true);
                }
            }
        }
    }

    /**
    * @return row index
    * */
    public int getIndex() {
        return index;
    }

    /**
     * @return all the spaces in this row
     */
    public List<Space> getSpaces(){
        return spaces;
    }

    @Override
    public Iterator<Space> iterator() {
        return spaces.iterator();
    }
}
