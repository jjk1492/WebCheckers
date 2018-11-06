package com.webcheckers.model;

import com.webcheckers.model.Message.Type;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Deque;
import java.util.LinkedList;

/**
 * class representing the game, containing players and a board for each player
 */
public class Game {

    public static final int NUM_ROWS = 8;
    private final int NUM_PIECES = 12;
    
    private Player redPlayer;
    private Player whitePlayer;
    private Color activeColor;
    private Board redBoard;
    private Board whiteBoard;
    private Deque<Move> pendingMoves;
    private int redPiecesRemaining;
    private int whitePiecesRemaining;

    /**
     * constructor for the game, creates new boards for each player after they are assigned
     * @param redPlayer - red player
     * @param whitePlayer - white player
     */
    public Game(Player redPlayer, Player whitePlayer) {
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.activeColor = Color.RED;
        this.redBoard = new Board();
        this.whiteBoard = new Board();
        redBoard.fillBoard(Color.RED);
        whiteBoard.fillBoard(Color.WHITE);
        pendingMoves = new LinkedList<>();
        redPiecesRemaining = NUM_PIECES;
        whitePiecesRemaining = NUM_PIECES;
    }

    /**
     * get the red player
     * @return Player
     */
    public Player getRedPlayer() {
        return redPlayer;
    }

    /**
     * get the white player
     * @return Player
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * get the red player's board
     * @return Board
     */
    public Board getRedBoard() {
        return redBoard;
    }

    /**
     * get the white player's board
     * @return Board
     */
    public Board getWhiteBoard(){
        return whiteBoard;
    }

    /**
     * get the color of the active player
     * @return Color
     */
    public Color getActiveColor() {
        return activeColor;
    }

    /**
     * get the active player
     * @return Player
     */
    public Player getActivePlayer() {
        if ( activeColor.equals( Color.RED ) ) {
            return redPlayer;
        }
        return whitePlayer;
    }

    /**
     * get remaining number of pieces that the red player has
     * @return int
     */
    public int getRedPiecesRemaining() {
        return redPiecesRemaining;
    }

    /**
     * get the remaining number of pieces that the white player has
     * @return number of pieces remaining
     */
    public int getWhitePiecesRemaining() {
        return whitePiecesRemaining;
    }

    /**
     * change whose turn it is
     */
    public void swapTurn() {
        if ( activeColor.equals( Color.RED ) ) {
            activeColor = Color.WHITE;
        }
        else {
            activeColor = Color.RED;
        }
    }

    /**
     * applies the pending moves to the board
     */
    public void applyTurn() {
        Board activeBoard;
        Board opponentBoard;
        if ( activeColor.equals( Color.WHITE ) ) {
            activeBoard = whiteBoard;
            opponentBoard = redBoard;
        }
        else {
            activeBoard = redBoard;
            opponentBoard = whiteBoard;
        }
        Move move;
        while ( ( move = pendingMoves.pollLast() ) != null ) {
            Piece subject = activeBoard.getPiece(move.getStart());
            activeBoard.applyMove( move, subject );
            Move inverseMove = move.getInverse();
            opponentBoard.applyMove( inverseMove, subject );
        }
        swapTurn();
   }

    /**
     * checks if a move is valid
     * @param move move that is trying to be made
     * @return info or error message
     */
    public Message tryMove( Move move ){
        Message message;
        if ( activeColor == Color.RED ) {
            message = redBoard.validateMove( move, activeColor );
        }
        else {
            message = whiteBoard.validateMove( move, activeColor );
        }
        if ( message.getType().equals( Type.info ) ) {
            pendingMoves.push( move );
        }
        return message;
    }

    /**
     * checks if there are pending moves that can be backed up
     * @return
     */
    public boolean backupMove(){
        if(!pendingMoves.isEmpty()){
            pendingMoves.pop();
            return true;
        }
        return false;
    }


    /**
     * checks if 2 games are the same game
     * @param o object to check
     * @return true if the 2 games are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return redPlayer.equals(game.redPlayer) && whitePlayer.equals(game.whitePlayer);
    }

    /**
     * creates a hashcode for a game
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(redPlayer, whitePlayer);
    }
}
