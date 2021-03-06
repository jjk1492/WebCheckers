package com.webcheckers.model;

import com.webcheckers.model.Message.Type;

import java.util.*;

/**
 * class representing the game, containing players and a board for each player
 */
public class Game {

    private Player redPlayer;
    private Player whitePlayer;
    private Color activeColor;
    private Board board;
    private Deque<Move> pendingMoves;
    private String gameWinner;

    /**
     * constructor for the game, creates new boards for each player after they are assigned
     * @param redPlayer - red player
     * @param whitePlayer - white player
     */
    public Game(Player redPlayer, Player whitePlayer) {
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.activeColor = Color.RED;
        this.board = new Board();
        pendingMoves = new LinkedList<>();
        gameWinner = null;
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
        return new Board( board );
    }

    /**
     * get the white player's board
     * @return Board
     */
    public Board getWhiteBoard(){
        return board.flipped();
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
     * get the winner of the game
     * @return name of winner or null if players still have pieces
     */
    public String getGameWinner() {
        return gameWinner;
    }


    public Move getValidMove() {
        List<Move> moves = getValidMoves();
        if ( moves == null || moves.size() == 0 ) {
            return null;
        }
        return moves.get(0);
    }


    public List<Move> getValidMoves() {
        Board copy = new Board( board );
        for ( Move pendingMove : pendingMoves ) {
            copy.applyMove( pendingMove );
        }
        return copy.getValidMoves();
    }



    /**
     * end of turn operations
     */
    private void endTurn() {
        activeColor = activeColor.getOpposite();
        board.endTurn();
    }

    /**
     * applies the pending moves to the board
     */
    public boolean applyTurn() {
        if ( pendingMoves.size() == 0 ) {
            return false;
        }
        if(forceJump()){
            return false;
        }
        Move move;
        while ( ( move = pendingMoves.pollFirst() ) != null ) {
            board.applyMove( move );
        }
        if(!board.hasUnblockedPieces(activeColor.getOpposite())){
            gameWinner = getActivePlayer().getName();
        }
        endTurn();
        return true;
    }

    /**
     * checks if there is still a jump to be made at the end of the turn
     * @return true if there is a jump that needs to be made
     */
    public boolean forceJump(){
        Board copyBoard;
        copyBoard = new Board( board );
        for ( Move pendingMove : pendingMoves ) {
            copyBoard.applyMove( pendingMove );
        }
        Move move = pendingMoves.peekLast();
//        if ( activeColor == Color.WHITE ) {
//            move = move.getInverse();
//        }
        if( move != null && move.isJump() ) {
            return copyBoard.canJump( move.getEnd() );
        }
        return false;
    }

    /**
     * checks if a move is valid
     * @param move move that is trying to be made
     * @return info or error message
     */
    public Message tryMove( Move move ){
        Message message;
        Board copyBoard;

        copyBoard = new Board( board );
        for ( Move pendingMove : pendingMoves ) {
            copyBoard.applyMove(pendingMove);
        }
        message = copyBoard.validateMove( move );
        if ( message.getType().equals( Type.info ) ) {
            pendingMoves.offerLast( move );
        }
        return message;
    }


    /**
     * checks if there are pending moves that can be backed up
     * @return true if something popped
     */
    public boolean backupMove(){
        if( !pendingMoves.isEmpty() ){
            pendingMoves.pollLast();
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
