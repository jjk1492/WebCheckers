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
        for (int i = 0; i < 8; i++){
            Space newSpace = new Space(i);
            spaces.add(newSpace);
        }

    }

    public void fillRedRow(){
        for( Space space : spaces){
            if( index%2 == 0){
                if( space.getCellIdx()%2 == 1){
                    space.setPiece( new Piece(Piece.Color.RED, Piece.Type.SINGLE));
                }
            }
            else {
                if( space.getCellIdx()%2 == 0){
                    space.setPiece( new Piece(Piece.Color.RED, Piece.Type.SINGLE));
                }
            }
        }
    }

    public void fillWhiteRow(){
        for( Space space : spaces){
            if( index%2 == 0){
                if( space.getCellIdx()%2 == 1){
                    space.setPiece( new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));
                }
            }
            else {
                if( space.getCellIdx()%2 == 0){
                    space.setPiece( new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));
                }
            }
        }
    }

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
