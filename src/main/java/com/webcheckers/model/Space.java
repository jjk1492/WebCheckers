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

    public Space(Space space){
        cellIdx = space.getCellIdx();
        isValid = space.isValid();
        if (space.getPiece() == null)
            piece = null;
        else
            piece = new Piece(space.getPiece());
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
