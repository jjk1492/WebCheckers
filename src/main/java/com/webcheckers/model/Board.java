package com.webcheckers.model;

import java.util.*;

import static com.webcheckers.model.Color.RED;
import static com.webcheckers.model.Color.WHITE;

/**
 * Board class in the model tier
 * @author several
 */
public class Board implements Iterable<Row> {

    public static final int ROWS = 8;
    public static final int COLS = 8;

    private Space[][] spaces;
    private Color activeColor;
    private boolean canStep;
    private boolean canJump;

    /**
     * constructs new Board
     */
    public Board() {
        Space space;
        Piece piece;
        activeColor = RED;
        canJump = true;
        canStep = true;
        spaces = new Space[ROWS][COLS];
        for (int row = 0; row < ROWS ; row++){
            for ( int col = 0 ; col < COLS ; col++ ) {
                space = spaces[row][col] = new Space(col);
                space.setValid( false );
                if (row % 2 + col % 2 == 1) {
                    if (row > 4) {
                        piece = new Piece( RED, Piece.State.OPEN );
                    } else if (row < 3) {
                        piece = new Piece( Color.WHITE, Piece.State.OPEN );
                    } else {
                        piece = null;
                        space.setValid( true );
                    }
                    space.setPiece( piece );
                }
            }
        }
        updatePieceStates();
    }

    /**
     * copy constructor, public accessible
     * @param board the board to copy
     */
    public Board( Board board ) {
        this( board, false );
    }

    /**
     * copy constructor for Board usable by other constructor and flipped
     * @param board the board to copy
     * @param flip whether to flip the board
     */
    private Board( Board board, boolean flip ) {
        canJump = board.canJump;
        canStep = board.canStep;
        activeColor = board.activeColor;
        spaces = new Space[ROWS][COLS];
        Position position;
        for ( int row = 0 ; row < ROWS ; row++ ) {
            for ( int col = 0 ; col < COLS ; col++ ) {
                position = new Position( row, col );
                if ( flip ) {
                    position = position.getInverse();
                }
                spaces[row][col] = board.spaces[position.getRow()][position.getCell()].getInverse();
            }
        }
    }

    /**
     * Used to flip the board to be displayed
     * @return the flipped board
     */
    public Board flipped() {
        return new Board( this, true );
    }


    /**
     * boolean to make sure a position is a valid position on the board
     * @param position - the position in question
     * @return true iff the position is located on the board
     */
    public boolean positionInBounds(Position position ) {
        return ( position.getRow() >= 0 && position.getRow() < ROWS &&
                position.getCell() >= 0 && position.getCell() < COLS );
    }


    /**
     * retrieves the space at the specified position
     * @param position the position on the board to be checked for a space
     * @return the space at said position
     */
    public Space getSpace( Position position ) {
        if (positionInBounds(position)) {
            return spaces[position.getRow()][position.getCell()];
        }
        return null;
    }

    /**
     * retrieves the piece at the specified position
     * @param position the position on the board to be checked for a piece
     * @return the piece at said position
     */
    public Piece getPiece( Position position ) {
        if ( positionInBounds( position ) ) {
            Space space = getSpace( position );
            if ( space != null ) {
                return space.getPiece();
            }
        }
        return null;
    }

    /**
     * returns whether or not the color has more pieces on the board
     * @param color the color of the pieces
     * @return true if there is still pieces of that color that are unblocked, false otherwise
     */
    public boolean hasUnblockedPieces(Color color){
        Piece piece;
        for (int row = 0; row < ROWS; row++)
            for (int col = 0; col < COLS; col++) {
                piece = spaces[row][col].getPiece();
                if(piece != null && piece.getColor() == color && piece.getState() != Piece.State.BLOCKED)
                    return true;
            }
        return false;
    }

    /**
     * gets the Space the is halfway between the start and end position for a move; the space that
     * the piece that was taken was previously occupying
     * @param start the starting position for the calculation
     * @param end the end position for the calculation
     * @return the halfway point between these two positions
     */
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


    /**
     * checks many conditions to make sure that a move that a player wants to make is valid
     * @param move the move in question
     * @return a message based on whether or not the move was valid or invalid and why
     */
    public Message validateMove( Move move ) {
        if ( activeColor == Color.WHITE ) {
            move = move.getInverse();
        }
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

        if ( startPiece.getColor() != activeColor ) {
            return new ErrorMessage( "You can't move your opponent's pieces!" );
        }

        if ( move.isStep() ) {
            if ( !canStep ) {
                return new ErrorMessage( "You can't step after moving!" );
            }

            if ( hasOpenJump( startPiece.getColor() ) ) {
                return new ErrorMessage( "A jump is available, you must jump.");
            }

            if ( isValidStep( move ) ) {
                return new InfoMessage( "Your move was valid!" );
            }
            else {
                return new ErrorMessage( "That piece can't do that!" );
            }
        }
        else if ( move.isJump() ) {
            if ( !canJump ) {
                return new ErrorMessage( "Can't jump after taking a step!" );
            }
            if ( isValidJump( move ) ) {
                return new InfoMessage( "Valid jump!" );
            }
            else {
                Space halfway = getHalfway( start, end );
                if ( halfway == null || halfway.getPiece() == null ) {
                    return new ErrorMessage( "Can't jump over nothing!" );
                }
                else if( activeColor == halfway.getPiece().getColor() ) {
                    return new ErrorMessage( "You can't jump over your own piece!" );
                }
                else {
                    return new ErrorMessage( "That piece can't do that!" );
                }
            }
        }
        else {
            return new ErrorMessage( "That move is invalid!" );
        }
    }


    /**
     * applies a move
     * @param move the move to be applied
     */
    public void applyMove( Move move ) {
        if ( activeColor == WHITE ) {
            move = move.getInverse();
        }
//        System.out.println( "applying " + move + " for " + activeColor );
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
            canJump = false;
        }
        canStep = false;

        updatePieceStates();
    }

    /**
     * ends a player's turn
     */
    public void endTurn() {
        activeColor = activeColor.getOpposite();
        canStep = true;
        canJump = true;
    }

    /**
     * checks if a space is valid
     * @param rowIndex row to check
     * @param spaceIndex space to check
     * @return true if valid
     */
    public boolean spaceIsValid(int rowIndex, int spaceIndex){
        if ( !positionInBounds( new Position( rowIndex, spaceIndex ) ) ) {
            return false;
        }
        return spaces[rowIndex][spaceIndex].isValid();
    }


    public List<Move> getValidMoves() {
        List<Move> moves = getAllValidJumps();
        if ( moves != null && moves.size() > 0 ) {
            return moves;
        }
        return getAllValidSteps();
    }


    private List<Move> getAllValidJumps() {
        if ( !canJump ) {
            return null;
        }
        List<Move> moves = new LinkedList<>();
        List<Move> temp;
        for ( int row = 0 ; row < ROWS ; row++ ) {
            for ( int col = 0 ; col < COLS ; col++ ) {
                temp = getValidJumps( new Position( row, col ) );
                if ( temp != null ) {
                    moves.addAll( temp );
                }
            }
        }
        return moves;
    }

    private List<Move> getValidJumps( Position position ) {
        if ( !canJump ) {
            return null;
        }
        Piece piece = getPiece( position );
        if ( piece == null || piece.getColor() != activeColor ) {
            return null;
        }
        int row = position.getRow();
        int col = position.getCell();
        List<Move> moves = new LinkedList<>();
        Move move;
        for ( int x = -1 ; x < 2 ; x += 2 ) {
            for ( int y = -1 ; y < 2 ; y += 2 ) {
                move = new Move( position, new Position( row + ( 2 * y ), col + ( 2 * x ) ) );
                if ( isValidJump( move ) ) {
                    moves.add( move );
                }
            }
        }
        return moves;
    }


    private List<Move> getAllValidSteps() {
        if ( !canStep ) {
            return null;
        }
        List<Move> moves = new LinkedList<>();
        List<Move> temp;
        for ( int row = 0 ; row < ROWS ; row++ ) {
            for ( int col = 0 ; col < COLS ; col++ ) {
                temp = getValidSteps( new Position( row, col ) );
                if ( temp != null ) {
                    moves.addAll( temp );
                }
            }
        }
        return moves;
    }

    private List<Move> getValidSteps( Position position ) {
        if ( !canStep ) {
            return null;
        }
        Piece piece = getPiece( position );
        if ( piece == null || piece.getColor() != activeColor ) {
            return null;
        }
        int row = position.getRow();
        int col = position.getCell();
        List<Move> moves = new LinkedList<>();
        Move move;
        for ( int x = -1 ; x < 2 ; x += 2 ) {
            for ( int y = -1 ; y < 2 ; y += 2 ) {
                move = new Move( position, new Position( row + y, col + x ) );
                if ( isValidStep( move ) ) {
                    moves.add( move );
                }
            }
        }
        return moves;
    }


    /**
     * checks if there is a jump
     * @param color active player's color
     * @return true if jump available
     */
    private boolean hasOpenJump( Color color ) {
        Piece piece;
        for ( Space[] row : spaces ) {
            for ( Space space : row ) {
                piece = space.getPiece();
                if ( piece != null && piece.getColor() == color && piece.getState() == Piece.State.JUMP ) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * checks if a simple move is valid
     * @param move move that is trying to be made
     * @return true if valid
     */
    private boolean isValidStep( Move move ) {
        if( move.getStart() != null && positionInBounds( move.getStart() ) && move.getEnd() != null && positionInBounds( move.getEnd() ) && move.isStep() ) {

            Piece piece = getPiece(move.getStart());
            if ( piece == null ) {
                return false;
            }

            return getPiece( move.getEnd() ) == null && piece.isValidStep( move );
        }
        return false;
    }
    /**
     * validates that a jump is a valid move
     * @param move the move to be validated
     * @return a boolean; true if the jump is valid and false otherwise
     */
    private boolean isValidJump( Move move ){
        if( move.getStart() != null && positionInBounds( move.getStart() ) && move.getEnd() != null && positionInBounds( move.getEnd() ) && move.isJump() ) {
            Space halfwaySpace = getHalfway(move.getStart(), move.getEnd());
            if ( halfwaySpace == null || halfwaySpace.getPiece() == null ) {
                return false;
            }
            Piece piece = getPiece(move.getStart());
            if ( piece == null ) {
                return false;
            }
            if( getPiece( move.getEnd() ) == null && piece.isValidJump( move ) ) {

                Piece halfwayPiece = halfwaySpace.getPiece();
                return halfwayPiece != null && piece.getColor() != ( halfwayPiece.getColor() );
            }
        }
        return false;
    }


    /**
     * checks to make sure that the row index and space index are within their boundaries
     * @param position the position to check
     * @return true or false
     */
    private boolean canStep( Position position ) {
        if ( !positionInBounds( position ) ) {
            return false;
        }
        Piece piece = getPiece( position );
        if ( piece == null ) {
            return false;
        }
        int startRow = position.getRow();
        int startCol = position.getCell();
        int destRow = startRow + piece.getColor().getIncrement();
        int rightDestCol = startCol + 1;
        int leftDestCol = startCol - 1;
        Position rightJump = new Position( destRow, rightDestCol );
        Position leftJump = new Position( destRow, leftDestCol );

        return positionInBounds( rightJump ) &&
                isValidStep( new Move( position, rightJump ) )
                || positionInBounds( leftJump ) &&
                isValidStep( new Move( position, leftJump ) );
    }


    /**
     * checks if a piece can jump
     * @param position
     * @return true if they can jump
     */
    public boolean canJump( Position position ) {
        if ( !positionInBounds( position ) ) {
            return false;
        }
        Piece piece = getPiece( position );
        if ( piece == null ) {
            return false;
        }
        int startRow = position.getRow();
        int startCol = position.getCell();
        int destRow = startRow + piece.getColor().getIncrement() * 2;
        int rightDestCol = startCol + 2;
        int leftDestCol = startCol - 2;
        Position rightJump = new Position( destRow, rightDestCol );
        Position leftJump = new Position( destRow, leftDestCol );

        return positionInBounds( rightJump ) &&
                isValidJump( new Move( position, rightJump ) )
                || positionInBounds( leftJump ) &&
                isValidJump( new Move( position, leftJump ) );
    }


    /**
     * updates the status of a piece
     */
    private void updatePieceStates() {
        Space space;
        Piece piece;
        Position position;
        Piece.State state;
        for ( int row = 0 ; row < ROWS ; row++ ) {
            for ( int col = 0 ; col < COLS ; col++ ) {
                space = spaces[ row ][ col ];
                piece = space.getPiece();
                position = new Position( row, col );
                if ( piece != null ) {
                    if ( canJump( position ) ) {
                        state = Piece.State.JUMP;
                    }
                    else if ( canStep( position ) ) {
                        state = Piece.State.OPEN;
                    }
                    else {
                        state = Piece.State.BLOCKED;
                    }
                    piece.setState( state );
                }
            }
        }
    }


    @Override
    public Iterator<Row> iterator() {
        Collection<Row> linkedList = new LinkedList<>();
        for ( int row = 0 ; row < ROWS ; row++ ) {
            linkedList.add( new Row( row, Arrays.asList( spaces[row] ) ) );
        }
        return linkedList.iterator();
    }


//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        for ( int row = 0 ; row < ROWS ; row++ ) {
//            for ( int col = 0 ; col < COLS ; col++ ) {
////        for ( Row row : this ) {
////            for ( Space space : row ) {
//                Space space = spaces[row][col];
////                int row = row.getIndex();
////                int col = space.getCellIdx();
//                sb.append( String.format( "(%d,%d)", row, col ) );
//                sb.append( space.isValid() ? 'v' : 'i' );
//                if ( space.getPiece() == null ) {
//                    sb.append( "__ " );
//                }
//                else {
//                    String state = null;
//                    switch ( space.getPiece().getState() ) {
//                        case JUMP:
//                            state = "j";
//                            break;
//                        case OPEN:
//                            state = "o";
//                            break;
//                        case BLOCKED:
//                            state = "b";
//                            break;
//                    }
//                    sb.append( state.charAt( 0 ) );
//                    sb.append( space.getPiece().getColor() == RED ? 'r' : 'w' );
//                    sb.append( ' ' );
//                }
//            }
//            sb.append( '\n' );
//        }
//        return sb.toString();
//    }
}
