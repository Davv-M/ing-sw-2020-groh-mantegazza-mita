package it.polimi.ingsw.PSP38.server.model;

import java.util.*;

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

    private final Set<Worker> workers;
    private final Set<Tower> towers;
    private final Set<Cell> cellsWithDomes;

    /**
     * Board constructor that creates an empty board
     */

    public Board() {
        workers = Collections.unmodifiableSet(new HashSet<>());
        towers = Collections.unmodifiableSet(new HashSet<>());
        cellsWithDomes = Collections.unmodifiableSet(new HashSet<>());
    }

    /**
     * Board constructor that makes a board with the given list of cells
     *
     * @param towers   list of towers present on the board
     * @param workers list of workers present on the board
     * @param cellsWithDomes list of domes present on the board
     * @throws NullPointerException if the workers list or the towers list is null
     */

    private Board(Set<Worker> workers, Set<Tower> towers, Set<Cell> cellsWithDomes) throws NullPointerException{
        this.workers = Set.copyOf(Objects.requireNonNull(workers));
        this.towers = Set.copyOf(Objects.requireNonNull(towers));
        this.cellsWithDomes = Set.copyOf(Objects.requireNonNull(cellsWithDomes));
    }

    /**
     * Returns a copy of the board that contains
     * the given tower or the same board if the
     * argument is null
     *
     * @param tower the tower to place on the board
     * @return a new board with the given cell
     */

    public Board withTower(Tower tower) {
        if (tower == null) {
            return this;
        }
        Set<Tower> newBoardTowers = new HashSet<>(towers);
        newBoardTowers.add(tower);

        return new Board(workers, newBoardTowers, cellsWithDomes);
    }

    /**
     * Returns a copy of the board with the given worker
     * added or, if it was already present, with his position
     * modified or the same board if one of the arguments is null
     *
     * @param worker      the worker that we want add
     * @param newPosition the new worker's position
     * @return a new board with the worker added
     */

    public Board withWorker(Worker worker, Cell newPosition) {
        if (worker == null || newPosition == null){
            return this;
        }
        Set<Worker> newBoardWorkers = new HashSet<>(workers);
        newBoardWorkers.remove(worker);
        newBoardWorkers.add(new Worker(worker.getColor(), newPosition));

        return new Board(newBoardWorkers, towers, cellsWithDomes);
    }

    /**
     * Returns a copy of the board that contains
     * a dome at the given position or
     * the same board if the argument is null
     *
     * @param cell the cell at which the dome is placed
     * @return a new board with the dome added
     */

    public Board withDome(Cell cell) {
        if (cell == null){
            return this;
        }
        Set<Cell> newBoardDomes = new HashSet<>(cellsWithDomes);
        newBoardDomes.add(cell);

        return new Board(workers, towers, newBoardDomes);
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

    public Optional<Cell> directionNeighbor(Cell cell, Direction dir) {
        int neighborX = cell.getX() + dir.x();
        int neighborY = cell.getY() + dir.y();

        return isOutOfBounds(neighborX, neighborY) ? Optional.empty() : Optional.of(new Cell(neighborX, neighborY));
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
            Optional<Cell> possibleNeighbor = directionNeighbor(cell, dir);
            possibleNeighbor.ifPresent(neighbors::add);
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

    /**
     * Returns a map linking the workers (values) to the cells they occupy (keys).
     *
     * @return a map linking workers and the cells they occupy
     */

    public Map<Cell, Worker> getWorkersPositions() {
        Map<Cell, Worker> occupiedCells = new HashMap<>();
        for (Worker w : workers) {
            occupiedCells.put(w.getPosition(), w);
        }
        return occupiedCells;
    }

    /**
     * Returns a map linking the towers (values) to the cells they occupy (keys).
     *
     * @return a map linking towers and the cells they occupy
     */

    public Map<Cell, Tower> getTowersPositions() {
        Map<Cell, Tower> occupiedCells = new HashMap<>();
        for (Tower t : towers) {
            occupiedCells.put(t.getPosition(), t);
        }
        return occupiedCells;
    }

    /**
     * Determines if the given cell cointains a dome
     *
     * @param cell the cell that we want to check
     * @return <b>true</b> if the cell contains a dome , <b>false</b> otherwise
     */

    public boolean hasDomeAt(Cell cell){
        return cellsWithDomes.contains(cell);
    }

    /**
     * Determines if the given cell cointains a worker
     *
     * @param cell the cell that we want to check
     * @return <b>true</b> if the cell contains a worker , <b>false</b> otherwise
     */

    public boolean hasWorkerAt(Cell cell){
        return getWorkersPositions().containsKey(cell);
    }

    /**
     * Returns the height of the given cell
     *
     * @param cell the cell
     * @return the height
     */

    public int heightOf(Cell cell){
        return !getTowersPositions().containsKey(cell) ? 0 : getTowersPositions().get(cell).getHeight();
    }
}
