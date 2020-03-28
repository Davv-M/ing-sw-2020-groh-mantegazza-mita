package it.polimi.ingsw.PSP038.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * Two-dimensional Board game represented by a one-dimensional list of {@Code Cell.COUNT}
 * sequences of blocks in row major order.
 *
 * @author Maximilien Groh (10683107)
 */

public final class Board {

    private final List<ICell> cells;

    /**
     * @param cells list of cells that make up the board
     * @throws IllegalArgumentException if the given list does not contain
     *                                  precisely {@code Cell.COUNT} cells
     */

    public Board(List<ICell> cells) throws IllegalArgumentException {
        if (cells == null || cells.size() != Cell.COUNT) {
            throw new IllegalArgumentException(
                    "The game board must be made of precisely " + Cell.COUNT
                            + " blocks");
        }
        this.cells = unmodifiableList(new ArrayList<>(cells));
        ;
    }

    /**
     * Returns a board made of free cells
     *
     * @return Board made of free cells
     */

    public Board withFreeCells() {
        List<ICell> freeCells = new LinkedList<>();

        for (int row = 0; row < Cell.ROWS; ++row) {
            for (int col = 0; col < Cell.COLUMNS; ++col) {
                freeCells.add(new Cell(col, row));
            }
        }
        return new Board(freeCells);
    }

    /**
     * Returns the cell of the board at the
     * given coordinates
     *
     * @param x horizontal coordinate of the cell
     * @param y vertical coordinate of the cell
     * @return Cell at given coordinates
     */

    public ICell cellAt(int x, int y) {
        return cells.get(rowMajorIndex(x, y));
    }

    private static int rowMajorIndex(int x, int y) {
        return y * Cell.COLUMNS + x;
    }
}
