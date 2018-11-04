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

    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Board getRedBoard() {
        return redBoard;
    }

    public Board getWhiteBoard(){
        return whiteBoard;
    }

    public Color getActiveColor() {
        return activeColor;
    }

    public Player getActivePlayer() {
        if ( activeColor.equals( Color.RED ) ) {
            return redPlayer;
        }
        return whitePlayer;
    }

    public void swapTurn() {
        if ( activeColor.equals( Color.RED ) ) {
            activeColor = Color.WHITE;
        }
        else {
            activeColor = Color.RED;
        }
    }

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

    public Message tryMove( Move move ){
        Message message;
        if ( activeColor == Color.RED ) {
            message =redBoard.validateMove( move );
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

    public Deque<Move> backupMove(){
        return pendingMoves;
    }


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

    @Override
    public int hashCode() {
        return Objects.hash(redPlayer, whitePlayer, activeColor, redBoard, whiteBoard);
    }
}
