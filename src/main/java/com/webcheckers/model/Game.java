package com.webcheckers.model;


import java.util.Objects;

public class Game {

    private Player redPlayer;
    private Player whitePlayer;
    private Player currentPlayer;
    private Board board;

    public Game(Player redPlayer, Player whitePlayer) {
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.currentPlayer = redPlayer;
        this.board = new Board();
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

    public Board getBoard() {
        return board;
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
                Objects.equals(board, game.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(redPlayer, whitePlayer, currentPlayer, board);
    }
}
