package com.webcheckers.model;

public class Space {

    private int cellIdx;
    private boolean isValid;
    private Piece piece;

    public Space(int cellIdx){
        this.cellIdx = cellIdx;
        this.isValid = false;
        this.piece = null;
    }

    public int getCellIdx() {
        return cellIdx;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid){
        this.isValid = valid;
    }

    public void setPiece( Piece newPiece){
        this.piece = newPiece;
    }
}
