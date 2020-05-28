package it.polimi.ingsw.PSP38.server.model;

import static it.polimi.ingsw.PSP38.server.utilities.ArgumentChecker.requireBetween;

/**
 * Immutable class that represents a board's cell.
 *
 * @author Maximilien Groh (10683107)
 */

public final class Cell {
    private final int x;
    private final int y;

    /**
     * @param x           x coordinate of the cell
     * @param y           y coordinate of the cell
     * @throws IllegalArgumentException if the coordinates are out of bounds
     */

    public Cell(int x, int y) throws IllegalArgumentException {
        this.x = requireBetween(0, Board.COLUMNS - 1, x);
        this.y = requireBetween(0, Board.ROWS - 1, y);
    }

    /**
     * Returns cell's x coordinate.
     *
     * @return cell's x coordinate.
     */

    public int getX() {
        return x;
    }

    /**
     * Returns cell's x coordinate.
     *
     * @return cell's x coordinate.
     */

    public int getY() {
        return y;
    }

    /**
     * Returns cell's index in row major order.
     *
     * @return cell's index in row major order.
     */

    public int rowMajorIndex() {
        return getY() * Board.COLUMNS + getX();
    }

    public boolean isOnPerimeter(){
        return x == Board.COLUMNS - 1 || y == Board.ROWS - 1;
    }

    @Override
    public int hashCode() {
        return rowMajorIndex();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Cell other = (Cell) obj;
        return getX() == other.getX() && getY() == other.getY();
    }

    @Override
    public String toString() {
        return "position : (" + x + "," + y + ")";
    }
}