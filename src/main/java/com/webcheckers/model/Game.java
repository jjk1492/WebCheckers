package com.webcheckers.model;


import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.Vector;

/**
 * class representing the game, containing players and a board for each player
 */
public class Game {

    private Player redPlayer;
    private Player whitePlayer;
    private Color activeColor;
    private Board redBoard;
    private Board whiteBoard;
    private Stack<Move> lastValidMoves;

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
        lastValidMoves = new Stack<>();
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
                lastValidMoves.clear();
            return redPlayer;
        }
        return whitePlayer;
    }

    /**
     * get last valid move made
     * @return last valid move made
     */
    public Stack<Move> getLastValidMoves(){ return lastValidMoves; }

    public void swapTurn() {
        if ( activeColor.equals( Color.RED ) ) {
            activeColor = Color.WHITE;
            lastValidMoves.clear();
        }
        else {
            activeColor = Color.RED;
            lastValidMoves.clear();
        }
    }

    public Message validateMove( Move move ){
        Position starting = move.getStart();
        Position ending = move.getEnd();

        //Check if the move is possible( the end position must be one row up )
        if( starting.getRow() - 1 == ending.getRow() ){

            //Check if the space is a valid piece to drop on( must be one
            if( starting.getCell() + 1 == ending.getCell() || starting.getCell() - 1 == ending.getCell() ){

                //Check that the ending space isn't occupied
                if( activeColor == Color.RED) {
                    if (redBoard.spaceIsValid(ending.getRow(), ending.getCell())) {
                        lastValidMoves.push(move);
                        return new InfoMessage("Your move was valid.");
                    } else {
                        return new ErrorMessage("The space you choose is occupied.");
                    }
                }
                 else{
                    if (whiteBoard.spaceIsValid(ending.getRow(), ending.getCell())) {
                        lastValidMoves.push(move);
                        return new InfoMessage("Your move was valid.");
                    } else {
                        return new ErrorMessage("The space you choose is occupied.");
                    }

                }
            }
            else{
                return new ErrorMessage("Pieces must move one space diagonally.");
            }
        }
        else {
            return new ErrorMessage("Pieces must move no more than one row up");
        }
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
