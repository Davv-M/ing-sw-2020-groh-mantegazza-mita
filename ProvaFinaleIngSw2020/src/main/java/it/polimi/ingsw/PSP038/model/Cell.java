package it.polimi.ingsw.PSP038.model;

import java.util.Optional;

import static it.polimi.ingsw.PSP038.model.ArgumentChecker.requireBetween;

public final class Cell implements ICell {
    public static final int ROWS = 5;
    public static final int COLUMNS = 5;
    public static final int COUNT = ROWS * COLUMNS;

    int x;
    int y;

    /**
     * Constructs a cell with Cartesian coordinates.
     *
     * @param x Horizontal coordinate
     * @param y Vertical coordinate
     * @throws IllegalArgumentException if x isn't between 0 and {@code COLUMNS} or if y isn't
     *                                  between 0 and {@code ROWS}
     */

    public Cell(int x, int y) throws IllegalArgumentException {
        this.x = requireBetween(0, COLUMNS - 1, x);
        this.y = requireBetween(0, ROWS - 1, y);
    }

    @Override
    public int x() {
        return x;
    }

    @Override
    public int y() {
        return y;
    }

    @Override
    public int height(){
        return 0;
    }

    @Override
    public Optional<ICell> neighbor(Direction dir) {
        int neighborX = x + dir.x();
        int neighborY = y + dir.y();
        return isOutOfBounds(neighborX, neighborY) ? Optional.empty() : Optional.of(new Cell(neighborX, neighborY));
    }

    private static boolean isOutOfBounds(int x, int y) {
        return x < 0 || y < 0 || x >= COLUMNS || y >= ROWS;
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
        return x() == other.x() && y() == other.y();
    }

    @Override
    public String toString() {
        return "(" + x() + "," + y() + ")";
    }
}
