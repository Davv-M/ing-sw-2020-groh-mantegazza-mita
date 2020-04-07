package it.polimi.ingsw.PSP38.model;

import static it.polimi.ingsw.PSP38.utilities.ArgumentChecker.requireBetween;

/**
 * Immutable class that represents a board's cell.
 *
 * @author Maximilien Groh (10683107)
 */

public final class Cell {
    public final int TOWER_MAX_HEIGHT = 3;

    private final int x;
    private final int y;
    private final int towerHeight;
    private final boolean hasDome;

    /**
     * @param x           x coordinate of the cell
     * @param y           y coordinate of the cell
     * @param towerHeight the height of the cell's tower
     * @param hasDome     boolean that indicates whether the cell has a dome or not
     * @throws IllegalArgumentException if the coordinates are out of bounds or if
     *                                  the given tower height is not between 0 and {@code TOWER_MAX_HEIGHT}
     */

    public Cell(int x, int y, int towerHeight, boolean hasDome) throws IllegalArgumentException {
        this.x = requireBetween(0, Board.COLUMNS - 1, x);
        this.y = requireBetween(0, Board.ROWS - 1, y);
        this.towerHeight = requireBetween(0, TOWER_MAX_HEIGHT, towerHeight);
        this.hasDome = hasDome;
    }

    /**
     * Constructs a free cell with the given coordinates
     *
     * @param x x coordinate of the cell
     * @param y y coordinate of the cell
     * @throws IllegalArgumentException if the coordinates are out of bounds
     */

    public Cell(int x, int y) throws IllegalArgumentException {
        this(x, y, 0, false);
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
     * Returns the cell's tower's height
     *
     * @return the cell's tower's height
     */

    public int getTowerHeight() {
        return towerHeight;
    }

    /**
     * Determines whether the cell has a dome or not
     *
     * @return <b>true</b> if it has a dome , <b>false</b> otherwise
     */

    public boolean hasDome() {
        return hasDome;
    }

    /**
     * Returns a copy of the cell with the specified tower height
     *
     * @param newTowerHeight the height of the new tower
     * @return  a copy of the cell with the specified tower height
     */

    public Cell withTowerHeight(int newTowerHeight) {
        return new Cell(x, y, newTowerHeight, hasDome);
    }

    /**
     * Returns a copy of the cell with a dome on top
     *
     * @return Returns a copy of the cell with a dome on top
     */

    public Cell withDome() {
        return new Cell(x, y, towerHeight, true);
    }

    @Override
    public int hashCode() {
        return getY() * Board.COLUMNS + getX();
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
        return "(" + x + "," + y + "," + towerHeight + "," + hasDome + ")";
    }
}