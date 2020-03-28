package it.polimi.ingsw.PSP038.model;

import static it.polimi.ingsw.PSP038.model.ArgumentChecker.requireBetween;

/**
 * Immutable class representing the cells that make up the base layer
 * of the board. They can be decorated using the CellDecorator class.
 * Each cell has Cartesian coordinates ({@code x, y}) and height 0;
 *
 * @author Maximilien Groh (10683107)
 */

public final class Cell implements ICell {
    public static final int ROWS = 5;
    public static final int COLUMNS = 5;
    public static final int COUNT = ROWS * COLUMNS;

    private final int x;
    private final int y;

    /**
     * Constructs a cell with Cartesian coordinates.
     *
     * @param x Horizontal coordinate
     * @param y Vertical coordinate
     * @throws IllegalArgumentException if x isn't between 0 and {@code COLUMNS} or if y isn't
     *                                  between 0 and {@code ROWS}
     */

    public Cell(int x, int y) throws IllegalArgumentException {
        this.x = requireBetween(0, COLUMNS, x);
        this.y = requireBetween(0, ROWS, y);
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
    public int height() {
        return 0;
    }

    @Override
    public String toString() {
        return "(" + x() + "," + y() + ")";
    }
}
