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
    private List<Piece> redPieces;
    private List<Piece> whitePieces;

    /*
     * constructs new Board
     * */
    public Board(){
        this.rows = new ArrayList<>();
        for (int i = 0; i < 8; i++){
            rows.add(new Row(i));
        }
        this.redPieces = new ArrayList<>();
        this.whitePieces = new ArrayList<>();
    }

    public void fillRedBoard(){
        for( Row row: rows){
            int index = row.getIndex();
            switch (index) {
                case 0:
                    row.fillWhiteRow(this);
                    break;
                case 1:
                    row.fillWhiteRow(this);
                    break;
                case 2:
                    row.fillWhiteRow(this);
                    break;
                case 3:
                    row.validateRow();
                    break;
                case 4:
                    row.validateRow();
                    break;
                case 5:
                    row.fillRedRow(this);
                    break;
                case 6:
                    row.fillRedRow(this);
                    break;
                case 7:
                    row.fillRedRow(this);
                    break;
            }
        }
    }

    public void fillWhiteBoard(){
        for( Row row: rows){
            switch (row.getIndex()){
                case 0:
                    row.fillRedRow(this);
                    break;
                case 1:
                    row.fillRedRow(this);
                    break;
                case 2:
                    row.fillRedRow(this);
                    break;
                case 3:
                    row.validateRow();
                    break;
                case 4:
                    row.validateRow();
                    break;
                case 5:
                    row.fillWhiteRow(this);
                    break;
                case 6:
                    row.fillWhiteRow(this);
                    break;
                case 7:
                    row.fillWhiteRow(this);
                    break;
            }
        }
    }


    public Space getSpace( Position position ) {
        // TODO row index safety check
        if( ! (position.getRow() < 0 || position.getRow() > 7) ){
            return rows.get( position.getRow() ).getSpace( position.getCell() );
        }
        return null;
    }

    public Piece getPiece( Position position ) {
        return rows.get( position.getRow() ).getPiece( position.getCell() );
    }

    public Space getHalfway( Position start, Position end ) {
        double startRow = (double)start.getRow();
        double startCol = (double)start.getCell();
        double endRow = (double)end.getRow();
        double endCol = (double)end.getCell();
        double midRow = Math.abs( endRow + startRow ) / 2;
        double midCol = Math.abs( endCol + startCol ) / 2;
        if ( midRow == (int)midRow && midCol == (int)midCol ) {
            Position midPos = new Position( (int)midRow, (int)midCol);
            return getSpace( midPos );
        }
        return null;
    }


    public Message validateMove( Move move, Color activeColor ) {
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
            return new ErrorMessage( "You cannot move to an occupied space!" );
        }
        if ( move.isStep() ) {
            boolean validStep = startPiece.isValidStep( move );
            if ( validStep ) {
                // TODO handle stepping when a valid jump move is available for that player
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
                else {
                    Color pieceColor = halfway.getPiece().getColor();
                    if(activeColor.equals(Color.RED) &&  pieceColor.equals(Color.RED)){
                        return new ErrorMessage("You can't jump over your own piece!");
                    }
                    else if(activeColor.equals(Color.WHITE) &&  pieceColor.equals(Color.WHITE)){
                        return new ErrorMessage("You can't jump over your own piece!");
                    }

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
    public void applyMove( Move move, Piece subject ) {
        Position start = move.getStart();
        Position end = move.getEnd();

        Space startSpace = getSpace( start );
        Space destination = getSpace( end );
        startSpace.setPiece( null );
        startSpace.setValid( true );
        destination.setPiece(subject);
        destination.setValid( false );

        if ( move.isJump() ) {
            Space halfway = getHalfway( start, end );
            halfway.setValid( true );
            halfway.setPiece( null );
            Move jumpAgain = secondJump(move.getEnd());
            if( jumpAgain != null ){
                applyMove( jumpAgain, subject);
            }
        }

    }

    public Move secondJump(Position postJumpPos){
        int startRow = postJumpPos.getRow();
        int startCol = postJumpPos.getCell();

        Position rightJump;
        Position leftJump;
        if( startRow >= 2){
            if( startCol <= 5){
                rightJump = new Position(startRow - 2, startCol + 2);
                Move rightMove = new Move(postJumpPos, rightJump);
                if( isValidJump(rightMove) ){
                    return rightMove;
                }
            }
            if( startCol >= 2){
                leftJump = new Position( startRow - 2, startCol - 2);
                Move leftMove = new Move(postJumpPos, leftJump);
                if( isValidJump(leftMove) ){
                    return leftMove;
                }
            }

        }
        return null;
    }

    public boolean isValidJump(Move move){
        if( move.isJump() && move.getStart() != null && move.getEnd() != null){
            //Check that the halfway space is occupied
            Space halfwaySpace = getHalfway(move.getStart(), move.getEnd());
            if( !halfwaySpace.isValid() ){
                Piece halfwayPiece = halfwaySpace.getPiece();
                Piece currentPiece = getPiece(move.getStart());
                if( currentPiece.getColor() != halfwayPiece.getColor() ){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean spaceIsValid(int rowIndex, int spaceIndex){
        Row checkRow = rows.get(rowIndex);
        return checkRow.isSpaceValid(spaceIndex);
    }

    public void addPiece(Piece newPiece ){
        if( newPiece != null){
            if( newPiece.getColor() == Color.RED ){
                redPieces.add(newPiece);
            }
            else{
                whitePieces.add(newPiece);
            }
        }
    }

    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }
}
