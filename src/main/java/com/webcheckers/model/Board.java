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

    /*
    * constructs new Board
    * */
    public Board(){
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

    public Message validateMove( Move move ) {
        Position start = move.getStart();
        Position end = move.getEnd();
        System.out.println( start );
        System.out.println( end );
        Space startSpace = rows.get( start.getRow() ).getSpace( start.getCell() );
        Space endSpace = rows.get( end.getRow() ).getSpace( end.getCell() );
        // System.out.println( startSpace.toString() );
        // System.out.println( endSpace.toString() );
        if ( startSpace == null || endSpace == null ) {
            return new ErrorMessage( "Your move was not on valid spaces!" );
        }
        Piece startPiece = startSpace.getPiece();
        Piece endPiece = endSpace.getPiece();
        if ( startPiece == null ) {
            return new ErrorMessage( "Your move must begin with an occupied space!" );
        }
        if ( endPiece != null ) {
            return new ErrorMessage( "Your move must begin with an occupied space!" );
        }
        if ( move.isStep() ) {
            boolean validStep = startPiece.isValidStep( move );
            if ( validStep ) {
                return new InfoMessage( "Your move was valid!" );
            }
            else {
                return new ErrorMessage( "That piece can't do that!" );
            }
        }
        if ( move.isJump() ) {
            boolean validJump = startPiece.isValidJump( move );
            if ( validJump ) {
                int halfwayRow = ( start.getRow() + end.getRow() ) / 2;
                int halfwayCol = ( start.getCell() + end.getCell() ) / 2;
                Space halfway = rows.get( halfwayRow ).getSpace( halfwayCol );
                if ( halfway == null || halfway.getPiece() == null ) {
                    return new ErrorMessage( "Can't jump over nothing!" );
                }
                // TODO handle jumping over own pieces
                else {
                    return new InfoMessage( "Valid jump!" );
                }
            }
            return new ErrorMessage( "That piece can't do that!" );
        }
        return new ErrorMessage( "That move is invalid!" );
    }

    public boolean spaceIsValid(int rowIndex, int spaceIndex){
        Row checkRow = rows.get(rowIndex);
        return checkRow.isSpaceValid(spaceIndex);
    }

    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }



}
