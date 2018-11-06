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
    * */
    public Row(int index){
        spaces = new ArrayList<>();
        this.index = index;
        for (int i = 0; i < 8; i++){
            Space newSpace = new Space(i);
            spaces.add(newSpace);
        }

    }

    public Row(Row row){
        spaces = new ArrayList<>();
        index = row.getIndex();
        for (int i = 0; i < 8; i++){
            Space newSpace = new Space(row.getSpace(i));
            spaces.add(newSpace);
        }
    }

    /**
     * Called at the beginning of a game to add red pieces to a row
     */
    public void fillRow( Board board, Color color ){
        Piece newPiece;
        for( Space space : spaces){
            if( index%2 + space.getCellIdx()%2 == 1){
                if( index == 2 || index == 5) {
                    newPiece = new Piece(color, Piece.State.OPEN);
                }
                else{
                    newPiece = new Piece(color, Piece.State.BLOCKED);
                }
                space.setPiece(newPiece);
                board.addPiece(newPiece);
            }
        }
    }


    /**
     * Called at the beginning of a game to validate any space in a row that should be
     * able to have a piece dropped on it
     */
    public void validateRow(){
        for( Space space : spaces){
            space.setValid(index%2 + space.getCellIdx()%2 == 1);
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
    public List<Space> getSpaces() {
        return spaces;
    }

    public Piece getPiece( int cell ) {
        Space space = getSpace( cell );
        if ( space == null ) {
            return null;
        }
        return space.getPiece();
    }


    public Space getSpace( int cell ) {
        if ( validIndex( cell ) ) {
            return spaces.get( cell );
        }
        return null;
    }


    public void putPiece( int cell, Piece piece ) {
        Space space = getSpace( cell );
        space.setPiece( piece );
    }


    public boolean validIndex( int index ) {
        return index >= 0 && index < 8;
    }


    public boolean isSpaceValid(int spaceIndex){
        if( !validIndex( spaceIndex ) ){
            return false;
        }
        Space checkSpace = spaces.get(spaceIndex);
        return checkSpace.isValid();
    }

    @Override
    public Iterator<Space> iterator() {
        return spaces.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Row spaces1 = (Row) other;
        return index == spaces1.index &&
                Objects.equals(spaces, spaces1.spaces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spaces, index);
    }
}
