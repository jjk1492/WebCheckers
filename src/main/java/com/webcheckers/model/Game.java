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
    
    private Player redPlayer;
    private Player whitePlayer;
    private Color activeColor;
    private Board redBoard;
    private Board whiteBoard;
    private Deque<Move> pendingMoves;

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
        redBoard.fillRedBoard();
        whiteBoard.fillWhiteBoard();
        pendingMoves = new LinkedList<>();
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
     *
     * @param move
     * @return
     */
    public Message tryMove( Move move ){
        Message message;
        if ( activeColor == Color.RED ) {
            message = redBoard.validateMove( move );
        }
        else {
            message = whiteBoard.validateMove( move );
        }
        if ( message.getType().equals( Type.info ) ) {
            pendingMoves.push( move );
        }
        return message;
    }

    public Message submitTurn(){
        return new ErrorMessage("need to implement submitTurn in Game class");
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
        return Objects.equals(redPlayer, game.redPlayer) &&
                Objects.equals(whitePlayer, game.whitePlayer) &&
                Objects.equals(activeColor, game.activeColor) &&
                Objects.equals(redBoard, game.redBoard) &&
                Objects.equals(whiteBoard, game.whiteBoard);
    }

    /**
     * creates a hashcode for a game
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(redPlayer, whitePlayer, activeColor, redBoard, whiteBoard);
    }
}
