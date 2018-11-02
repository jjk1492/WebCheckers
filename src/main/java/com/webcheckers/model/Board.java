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


    public Space getSpace( Position position ) {
        // TODO row index safety check
        return rows.get( position.getRow() ).getSpace( position.getCell() );
    }

    public Piece getPiece( Position position ) {
        return rows.get( position.getRow() ).getPiece( position.getCell() );
    }

    public Space getHalfway( Position start, Position end ) {
        double startRow = (double)start.getRow();
        double startCol = (double)start.getCell();
        double endRow = (double)end.getRow();
        double endCol = (double)end.getCell();
        double midRow = Math.abs( endRow - startRow ) / 2;
        double midCol = Math.abs( endCol - startCol ) / 2;
        if ( midRow == (int)midRow && midCol == (int)midCol ) {
            return getSpace( new Position( (int)midRow, (int)midCol ) );
        }
        return null;
    }


    public Message validateMove( Move move ) {
        Position start = move.getStart();
        Position end = move.getEnd();
        Space startSpace = getSpace( start );
        Space endSpace = getSpace( end );

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
                Space halfway = getHalfway( start, end );
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
    
    
    /**
     * applies a move
     * @pre move is valid (not my responsibility to check :)
     */
    public void applyMove( Move move ) {
        Position start = move.getStart();
        Position end = move.getEnd();

        Space startSpace = getSpace( start );
        Piece subject = startSpace.getPiece();
        startSpace.setPiece( null );
        Space destination = getSpace( end );
        
        if ( move.isJump() ) {
            getHalfway( start, end ).setPiece( null );
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
}
