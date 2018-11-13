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
    private String gameWon;

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
        gameWon = null;
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
     * checks if the game is won
     * @return name of winner or null if game is not won
     */
    public String gameWinner(){
        return gameWon;
    }

    /**
     * applies the pending moves to the board
     */
    public void applyTurn() {
//        System.out.println( "applying " + activeColor + "'s turn" );
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
            activeBoard.applyMove( move, activeColor );
            Move inverseMove = move.getInverse();
            opponentBoard.applyMove( inverseMove, activeColor.getOpposite() );
        }
        activeBoard.endTurn();
        opponentBoard.endTurn();
        if(activeBoard.getPieceCount(activeColor.getOpposite()) == 0)
            gameWon = getActivePlayer().getName();
        swapTurn();
   }

    /**
     * checks if a move is valid
     * @param move move that is trying to be made
     * @return info or error message
     */
    public Message tryMove( Move move ){
        Message message;
        Board activeBoard;
        if ( activeColor == Color.RED ) {
            activeBoard = redBoard;
        }
        else {
            activeBoard = whiteBoard;
        }
        activeBoard = new Board( activeBoard );
        for ( Move pendingMove : pendingMoves ) {
            activeBoard.applyMove(pendingMove, activeColor);
        }
        message = activeBoard.validateMove( move, activeColor );
        if ( message.getType().equals( Type.info ) ) {
//            System.out.println( "Good move, new game state" );
//            System.out.println( activeBoard.toString() );
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
