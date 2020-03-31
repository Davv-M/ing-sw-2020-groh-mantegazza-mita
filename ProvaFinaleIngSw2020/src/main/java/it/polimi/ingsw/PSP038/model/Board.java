package it.polimi.ingsw.PSP038.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.unmodifiableList;

/**
 * Two-dimensional Board game represented by a one-dimensional list of {@code board.TOTALCELLES}
 * sequences of blocks in row major order.
 *
 * @author Maximilien Groh (10683107)
 */

public class Board {

    private List<Cell> cells;
    public static final int ROWS = 5;
    public static final int COLUMNS = 5;
    public static final int TOTAL_CELLS = ROWS*COLUMNS;

    /**
     * Board constructor's that made board of free cells
     *
     * @return Board made of free cells
     */

    public Board() {
        cells = new LinkedList<>();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                cells.add(new Cell(row, col));
            }
        }
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
     * Returns the cell's index
     *
     * @param x vertical coordinate of the cell
     * @param y horizontal coordinate of the cell
     * @return cell' index
     */

    private int rowMajorIndex(int x, int y) {
        return (x * ROWS) + y;
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
        int neighborX = cell.getXCoordinate() + dir.x();
        int neighborY = cell.getYCoordinate() + dir.y();

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
     * Determines if the given coordinates are out of bound
     *
     * @param x horizontal coordinate of the cell
     * @param y vertical coordinate of the cell
     * @return <b>true</b> if it is , <b>false</b> otherwise
     */

    private boolean isOutOfBounds(int x, int y) {
        return x < 0 || y < 0 || x >= ROWS || y >= COLUMNS;
    }

}
