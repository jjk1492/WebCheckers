package com.webcheckers.model;


import java.util.Objects;

/**
 * class representing the game, containing players and a board for each player
 */
public class Game {

    private Player redPlayer;
    private Player whitePlayer;
    private Player currentPlayer;
    private Board redBoard;
    private Board whiteBoard;

    /**
     * constructor for the game, creates new boards for each player after they are assigned
     * @param redPlayer - red player
     * @param whitePlayer - white player
     */
    public Game(Player redPlayer, Player whitePlayer) {
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.currentPlayer = redPlayer;
        this.redBoard = new Board();
        this.whiteBoard = new Board();
        redBoard.fillRedBoard();
        whiteBoard.fillWhiteBoard();
    }


    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getRedBoard() {
        return redBoard;
    }

    public Board getWhiteBoard(){
        return whiteBoard;
    }

    public Color getActiveColor() {
        if(this.currentPlayer == redPlayer){
            return Color.RED;
        }else{
            return Color.WHITE;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(redPlayer, game.redPlayer) &&
                Objects.equals(whitePlayer, game.whitePlayer) &&
                Objects.equals(currentPlayer, game.currentPlayer) &&
                Objects.equals(redBoard, game.redBoard) &&
                Objects.equals(whiteBoard, game.whiteBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(redPlayer, whitePlayer, currentPlayer, redBoard, whiteBoard);
    }
}
