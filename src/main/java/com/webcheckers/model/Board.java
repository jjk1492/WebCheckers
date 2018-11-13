package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * Board class in the model tier
 * */
public class Board implements Iterable<Iterable<Space>> {

    //7 rows
//    private List<Row> rows;
    private Space[][] spaces;
//    private List<Piece> redPieces;
//    private List<Piece> whitePieces;
    private boolean canMove = true;

    /*
     * constructs new Board
     * */
    public Board(){
        Space space;
        Piece piece;
        spaces = new Space[8][8];
        for (int row = 0; row < 8; row++){
            for ( int col = 0 ; col < 8 ; col++ ) {
                space = spaces[row][col] = new Space(col);
                space.setValid( false );
                if (row % 2 + col % 2 == 1) {
                    space.setValid( true );
                    if (row > 4) {
                        piece = new Piece( Color.WHITE, Piece.State.OPEN );
                    } else if (row < 3) {
                        piece = new Piece( Color.RED, Piece.State.OPEN );
                    } else {
                        piece = null;
                        space.setValid( true );
                    }
                    space.setPiece( piece );
                }
            }
        }
    }

    /*
     * copy constructor for Board
     * */
    public Board(Board board){
//        this.rows = new ArrayList<>();
//        this.redPieces = new ArrayList<>();
//        this.whitePieces = new ArrayList<>();
//        for (int i = 0; i < 8; i++){
//            rows.add(new Row(board.rows.get(i)));
//        }
//        for (int i = 0; i < board.whitePieces.size(); i++){
//            whitePieces.add(new Piece(board.whitePieces.get(i)));
//        }
//        for (int i = 0; i < board.redPieces.size(); i++){
//            redPieces.add(new Piece(board.redPieces.get(i)));
//        }
        //TODO
    }

    public boolean positionInBounds(Position position ) {
        return ( position.getRow() >= 0 && position.getRow() < 8 &&
                position.getCell() >= 0 && position.getCell() < 8 );
    }


    public Space getSpace( Position position ) {
        if (positionInBounds(position)) {
            return spaces[position.getRow()][position.getCell()];
        }
        return null;
    }

    public Piece getPiece( Position position ) {
        if ( positionInBounds( position ) ) {
            Space space = getSpace( position );
            if ( space != null ) {
                return space.getPiece();
            }
        }
        return null;
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
        updatePieceStates( activeColor );
//        System.out.println(toString());
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
        if (!canMove) {
            return new ErrorMessage( "You cannot step after already moving!" );
        }
        if ( move.isStep() ) {
            boolean validStep;
            if ( startPiece.getColor() == activeColor ) {
                validStep = startPiece.isValidStep( move );
            }
            else {
                validStep = startPiece.isValidStep( move.getInverse() );
            }
            if ( validStep ) {
                // TODO handle stepping when a valid jump move is available for that player
                if( startPiece.getColor() ==  Color.RED ){
                    for( Piece redPiece : redPieces){
                        if( redPiece.getState() == Piece.State.JUMP ){
                            return new ErrorMessage("A jump is available, you must jump.");
                        }
                        else {
//                            System.out.println( redPiece + " (red) is in state: " + redPiece.getState() );
                        }
                    }
                }
                else{
                    for( Piece whitePiece : whitePieces ){
                        if( whitePiece.getState() == Piece.State.JUMP ){
                            return new ErrorMessage("A jump is available, you must jump.");
                        }
                        else {
//                            System.out.println( whitePiece + " (white) is in state: " + whitePiece.getState() );
                        }
                    }
                }
                return new InfoMessage( "Your move was valid!" );
            }
            else {
                return new ErrorMessage( "That piece can't do that!" );
            }
        }
        if ( move.isJump() ) {
            boolean validJump;
            if ( startPiece.getColor() == activeColor ) {
                validJump = startPiece.isValidJump( move );
            }
            else {
                validJump = startPiece.isValidJump( move.getInverse() );
            }
            if ( validJump ) {
                Space halfway = getHalfway( start, end );
                if ( halfway == null || halfway.getPiece() == null ) {
                    return new ErrorMessage( "Can't jump over nothing!" );
                }
                else {
                    Color pieceColor = halfway.getPiece().getColor();
                    if(activeColor == Color.RED &&  pieceColor == Color.RED ){
                        return new ErrorMessage("You can't jump over your own piece!");
                    }
                    else if( activeColor == Color.WHITE &&  pieceColor == Color.WHITE ){
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
    public void applyMove( Move move, Color myColor ) {
        Position start = move.getStart();
        Position end = move.getEnd();

        Space startSpace = getSpace( start );
        Space destination = getSpace( end );
        Piece subject = startSpace.getPiece();
        startSpace.setPiece( null );
        startSpace.setValid( true );
        destination.setPiece(subject);
        destination.setValid( false );

        if ( move.isJump() ) {
            Space halfway = getHalfway( start, end );
            halfway.setValid( true );
            halfway.setPiece( null );
        }
        else {
            canMove = false;
        }

        updatePieceStates( myColor );
    }

    public void endTurn() {
        canMove = true;
    }


    public boolean isValidJump(Move move){
        if( move.getStart() != null && move.getEnd() != null && move.isJump() ) {
            //Check that the halfway space is occupied
            Space halfwaySpace = getHalfway(move.getStart(), move.getEnd());
            Piece p = getPiece(move.getStart());
            if ( p == null ) {
//                System.out.println( "NULL START PIECE" );
                return false;
            }
            if( halfwaySpace != null && getPiece(move.getEnd() ) == null ) {
                Piece halfwayPiece = halfwaySpace.getPiece();
                Piece currentPiece = getPiece(move.getStart());
                if( currentPiece != null && halfwayPiece != null && currentPiece.getColor() != ( halfwayPiece.getColor() ) ) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean spaceIsValid(int rowIndex, int spaceIndex){
        if ( !positionInBounds( new Position( rowIndex, spaceIndex ) ) ) {
            return false;
        }
        return spaces[rowIndex][spaceIndex].isValid();
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

    public void updatePieceStates(Color color){
//        System.out.println( "Updating for " + color.toString() );
        for( Row row : rows){
            int rowIndex = row.getIndex();
            Position startPos;
            Position rightEndPos;
            Position leftEndPos;
            Move testMove;
            for( Space space : row.getSpaces() ){
                int spaceIndex = space.getCellIdx();
                startPos = new Position(rowIndex,spaceIndex);
                Piece currentPiece = space.getPiece();
                if( currentPiece != null ) {
                    if (currentPiece.getColor() == color) {

                        //Check for a jump
//                        if (rowIndex >= 2) {

                            //Check a right jump
//                            if (spaceIndex <= 5) {
                                rightEndPos = new Position(rowIndex - 2, spaceIndex + 2);
                                testMove = new Move(startPos, rightEndPos);
                                if (isValidJump(testMove)) {
                                    currentPiece.setState(Piece.State.JUMP);
                                    continue;
                                }
//                            }

                            //Check for a left jump
//                            if (spaceIndex >= 2) {
                                leftEndPos = new Position(rowIndex - 2, spaceIndex - 2);
                                testMove = new Move(startPos, leftEndPos);
                                if (isValidJump(testMove)) {
                                    currentPiece.setState(Piece.State.JUMP);
                                    continue;
                                }
//                            }
//                        }

                        //Check for a step
//                        if (rowIndex >= 1) {

                            //Check for a right step
//                            if (spaceIndex <= 6) {
                                if (spaceIsValid(rowIndex - 1, spaceIndex + 1)) {
                                    currentPiece.setState(Piece.State.OPEN);
                                }
//                            }
                            //Check for a left step
//                            if (spaceIndex >= 1) {
                                if (spaceIsValid(rowIndex - 1, spaceIndex - 1)) {
                                    currentPiece.setState(Piece.State.OPEN);
                                }
//                            }
//                        }
                    } else {

                        //Check for a jump
//                        if (rowIndex <= 5) {

                            //Check a right jump
//                            if (spaceIndex <= 5) {
                                rightEndPos = new Position(rowIndex + 2, spaceIndex + 2);
                                testMove = new Move(startPos, rightEndPos);
                                if (isValidJump(testMove)) {
                                    currentPiece.setState(Piece.State.JUMP);
                                    continue;
                                }
//                            }

                            //Check for a left jump
//                            if (spaceIndex >= 2) {
                                leftEndPos = new Position(rowIndex + 2, spaceIndex - 2);
                                testMove = new Move(startPos, leftEndPos);
                                if (isValidJump(testMove)) {
                                    currentPiece.setState(Piece.State.JUMP);
                                    continue;
                                }
//                            }
//                        }

                        //Check for a step
//                        if (rowIndex >= 1) {

                            //Check for a right step
//                            if (spaceIndex <= 6) {
                                if (spaceIsValid(rowIndex + 1, spaceIndex + 1)) {
                                    currentPiece.setState(Piece.State.OPEN);
                                }
//                            }
                            //Check for a left step
//                            if (spaceIndex >= 1) {
                                if (spaceIsValid(rowIndex + 1, spaceIndex - 1)) {
                                    currentPiece.setState(Piece.State.OPEN);
                                }
//                            }
//                        }

                    }
                }
            }
        }
//        System.out.println(toString());
    }


    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for ( int row = 0 ; row < rows.size() ; row++ ) {
            for ( int col = 0 ; col < rows.get( row ).getSpaces().size() ; col++ ) {
                Space space = rows.get( row ).getSpace( col );
                sb.append( String.format( "(%d,%d) v:%s ", row, col, space.isValid() ? "t" : "f" ) );
                if ( space.getPiece() == null ) {
                    sb.append( "no piece  " ) ;
                }
                else {
                    String state = null;
                    switch ( space.getPiece().getState() ) {
                        case JUMP:
                            state = "j";
                            break;
                        case OPEN:
                            state = "o";
                            break;
                        case BLOCKED:
                            state = "b";
                            break;
                    }
                    sb.append(String.format("c:%s s:%s   " , space.getPiece().getColor() == Color.RED ? "r" : "w", state) );
                }
            }
            sb.append( '\n' );
        }
        return sb.toString();
    }
}
