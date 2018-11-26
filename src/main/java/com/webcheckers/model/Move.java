package com.webcheckers.model;

import java.util.Objects;

/**
 * Move class in the model tier
 */
public class Move {

    private final Position start;
    private final Position end;

    /**
     * constructs Move object
     *
     * @param start initial Position
     * @param end   final Position
     */
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    public Move getInverse() {
        return new Move(start.getInverse(), end.getInverse());
    }

    /**
     * @return start Position
     */
    public Position getStart() {
        return start;
    }

    /**
     * @return end Position
     */
    public Position getEnd() {
        return end;
    }


    /**
     * checks if a Move is a step in any direction
     *
     * @return true if the move is a step
     */
    public boolean isStep() {
        int startRow = getStart().getRow();
        int startCol = getStart().getCell();
        int endRow = getEnd().getRow();
        int endCol = getEnd().getCell();
        if (Math.abs(startRow - endRow) != 1) {
            return false;
        }
        return Math.abs(startCol - endCol) == 1;
    }


    /**
     * Checks if a Move is a jump in any direction
     *
     * @return true if the move is a jump
     */
    public boolean isJump() {
        int startRow = getStart().getRow();
        int startCol = getStart().getCell();
        int endRow = getEnd().getRow();
        int endCol = getEnd().getCell();
        if (Math.abs(startRow - endRow) != 2) {
            return false;
        }
        return Math.abs(startCol - endCol) == 2;
    }


    /**
     * to string method for moving
     * @return
     */
    @Override
    public String toString() {
        return start.toString() + " to " + end.toString();
    }

    /**
     * checks if a move is equal
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) return false;
        Move move = (Move) other;
        return this.start.getRow() == move.start.getRow() && this.start.getCell() == move.start.getCell()
                && this.end.getRow() == move.end.getRow() && this.end.getCell() == move.end.getCell();

    }
}
