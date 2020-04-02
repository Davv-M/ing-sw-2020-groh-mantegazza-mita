package it.polimi.ingsw.PSP38.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.unmodifiableList;

/**
 * Immutable class representing a two-dimensional Board game
 * made by a one-dimensional list of {@code TOTAL_CELLS}
 * cells in row major order.
 *
 * @author Maximilien Groh (10683107)
 */

public final class Board {
    public static final int ROWS = 5;
    public static final int COLUMNS = 5;
    public static final int TOTAL_CELLS = ROWS * COLUMNS;

    private final List<Cell> cells;

    /**
     * Board constructor that makes a board of free cells
     */

    public Board() {
        cells = new LinkedList<>();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                cells.add(new Cell(col, row));
            }
        }
    }

    /**
     * Board constructor that makes a board with the given list of cells
     *
     * @param cells list of cells that make up the board
     * @throws IllegalArgumentException if the given list does not contain
     *                                  precisely {@code TOTAL_CELLS} cells
     */

    private Board(List<Cell> cells) throws IllegalArgumentException {
        if (cells == null || cells.size() != Board.TOTAL_CELLS) {
            throw new IllegalArgumentException(
                    "The game board must be made of precisely " + Board.TOTAL_CELLS
                            + " cells");
        }
        this.cells = unmodifiableList(new ArrayList<>(cells));
    }


    /**
     * Returns the cell of the board at the given coordinates
     *
     * @param x vertical coordinate of the cell
     * @param y horizontal coordinate of the cell
     * @return Cell at given coordinates
     */

    public Cell cellAt(int x, int y) {
        return cells.get(rowMajorIndex(x, y));
    }

    /**
     * Returns a copy of the board that contains
     * the given cell or the same board if the
     * argument is null
     *
     * @param newCell the cell to insert
     * @return a new board with the given cell
     */

    public Board withCell(Cell newCell) {
        if (newCell == null) {
            return this;
        }
        List<Cell> newBoardCells = new LinkedList<>(cells);
        int index = rowMajorIndex(newCell.getX(), newCell.getY());

        newBoardCells.remove(index);
        newBoardCells.add(index, newCell);

        return new Board(newBoardCells);
    }

    /**
     * Returns the cell's index
     *
     * @param x vertical coordinate of the cell
     * @param y horizontal coordinate of the cell
     * @return cell'index
     */

    private static int rowMajorIndex(int x, int y) {
        return (y * COLUMNS) + x;
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

    public Optional<Cell> DirectionNeighbor(Cell cell, Direction dir) {
        int neighborX = cell.getX() + dir.x();
        int neighborY = cell.getY() + dir.y();

        return isOutOfBounds(neighborX, neighborY) ? Optional.empty() : Optional.of(cellAt(neighborX, neighborY));
    }

    /**
     * Returns a list of cells representing the neighbors of the given cell
     *
     * @param cell the cell of which we want to find the neighbors
     * @return a list of cells representing the neighbors of the given cell
     */

    public List<Cell> neighborsCells(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();

        for (Direction dir : Direction.values()) {
            Optional<Cell> possibleNeighbor = DirectionNeighbor(cell, dir);
            if (possibleNeighbor.isPresent()) {
                neighbors.add(possibleNeighbor.get());
            }
        }
        return neighbors;
    }

    /**
     * Determines if the given coordinates are out of bounds
     *
     * @param x horizontal coordinate of the cell
     * @param y vertical coordinate of the cell
     * @return <b>true</b> if they are out of bounds , <b>false</b> otherwise
     */

    private boolean isOutOfBounds(int x, int y) {
        return x < 0 || y < 0 || x >= COLUMNS || y >= ROWS;
    }

}
