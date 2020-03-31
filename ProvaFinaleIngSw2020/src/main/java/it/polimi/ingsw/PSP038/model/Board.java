package it.polimi.ingsw.PSP038.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.unmodifiableList;

/**
 * Two-dimensional Board game represented by a one-dimensional list of {@code Cell.COUNT}
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

    private Board(List<ICell> cells) throws IllegalArgumentException {
        if (cells == null || cells.size() != Cell.COUNT) {
            throw new IllegalArgumentException(
                    "The game board must be made of precisely " + Cell.COUNT
                            + " blocks");
        }
        this.cells = unmodifiableList(new ArrayList<>(cells));
    }

    /**
     * Returns a board made of free cells
     *
     * @return Board made of free cells
     */

    public static Board withFreeCells() {
        List<ICell> freeCells = new LinkedList<>();

        for (int row = 0; row < Cell.ROWS; ++row) {
            for (int col = 0; col < Cell.COLUMNS; ++col) {
                freeCells.add(new Cell(col, row));
            }
        }
        return new Board(freeCells);
    }

    /**
     * Returns a copy of the board that contains
     * the given cell or the same board if the
     * argument is null
     *
     * @param cell the cell to insert
     * @return a new board with the given cell
     */

    public Board withCell(ICell newCell) {
        if (newCell == null) {
            return this;
        }
        List<ICell> newBoardCells = new LinkedList<>(cells);
        int index = rowMajorIndex(newCell.x(), newCell.y());

        newBoardCells.remove(index);
        newBoardCells.add(index, newCell);

        return new Board(newBoardCells);
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

    /**
     * Returns the neighbor of the given cell in the given direction if there is one,
     * the empty optional value otherwise.
     *
     * @param cell the cell of which we want to find the neighbor
     * @param dir  the direction where the neighbor lies
     * @return the neighbor of the given cell in the given direction if there is one,
     * the empty optional value otherwise.
     */

    public Optional<ICell> neighborOf(ICell cell, Direction dir) {
        int neighborX = cell.x() + dir.x();
        int neighborY = cell.y() + dir.y();

        return isOutOfBounds(neighborX, neighborY) ? Optional.empty() : Optional.of(cellAt(neighborX, neighborY));
    }

    /**
     * Returns a list of cells representing the neighbors of the given cell
     *
     * @param cell the cell of which we want to find the neighbors
     * @return a list of cells representing the neighbors of the given cell
     */

    public List<ICell> neighbors(ICell cell) {
        List<ICell> neighbors = new ArrayList<>();

        for (Direction dir : Direction.values()) {
            Optional<ICell> possibleNeighbor = neighborOf(cell, dir);
            if (possibleNeighbor.isPresent()) {
                neighbors.add(possibleNeighbor.get());
            }
        }
        return neighbors;
    }

    private static int rowMajorIndex(int x, int y) {
        return y * Cell.COLUMNS + x;
    }

    private static boolean isOutOfBounds(int x, int y) {
        return x < 0 || y < 0 || x >= Cell.COLUMNS || y >= Cell.ROWS;
    }

}
