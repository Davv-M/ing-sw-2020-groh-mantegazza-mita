package it.polimi.ingsw.PSP38.server.model;

import it.polimi.ingsw.PSP38.common.WorkerColor;

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
     * Board constructor that creates a board with the given workers, towers and domes.
     *
     * @param towers         set of towers present on the board
     * @param workers        set of workers present on the board
     * @param cellsWithDomes set of domes present on the board
     * @throws NullPointerException if one of the arguments is null
     */

    public Board(Set<Worker> workers, Set<Tower> towers, Set<Cell> cellsWithDomes) throws NullPointerException {
        this.workers = Set.copyOf(workers);
        this.towers = Set.copyOf(towers);
        this.cellsWithDomes = Set.copyOf(cellsWithDomes);
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
     * Returns a copy of the board that contains
     * the given worker or the same board if the
     * argument is null
     *
     * @param worker the worker to add
     * @return a new board with the worker added
     */

    public Board withWorker(Worker worker) throws IllegalArgumentException {
        if (worker == null) {
            return this;
        }

        Set<Worker> newBoardWorkers = new HashSet<>(workers);
        newBoardWorkers.add(worker);

        return new Board(newBoardWorkers, towers, cellsWithDomes);
    }

    /**
     * Returns a copy of the board without the
     * given worker or the same board if the
     * argument is null or if the worker wasn't
     * on the board
     *
     * @param worker the worker to remove
     * @return a new board with the worker removed
     */

    public Board withoutWorker(Worker worker) {
        if (worker == null) {
            return this;
        }

        Set<Worker> newBoardWorkers = new HashSet<>(workers);
        newBoardWorkers.remove(worker);

        return new Board(newBoardWorkers, towers, cellsWithDomes);
    }

    /**
     * Returns a copy of the board without the
     * workers of the specified color. If the
     * color is null or if there are no workers
     * of that color, the same board is returned.
     *
     * @param color the color of the workers to be removed
     * @return a new board with the workers removed
     */

    public Board withoutWorkers(WorkerColor color) {
        if (color == null) {
            return this;
        }

        Set<Worker> newBoardWorkers = new HashSet<>(workers);
        newBoardWorkers.removeIf(w -> w.getColor() == color);

        return new Board(newBoardWorkers, towers, cellsWithDomes);
    }

    /**
     * Returns a copy of the board without the
     * given tower or the same board if the
     * argument is null or if the tower wasn't
     * on the board
     *
     * @param tower the tower to remove
     * @return a new board with the tower removed
     */


    public Board withoutTower(Tower tower) {
        if (tower == null) {
            return this;
        }

        Set<Tower> newBoardTowers = new HashSet<>(towers);
        newBoardTowers.remove(tower);

        return new Board(workers, newBoardTowers, cellsWithDomes);
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
        if (cell == null) {
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

    public Optional<Cell> neighborOf(Cell cell, Direction dir) {
        int neighborX = cell.getX() + dir.x();
        int neighborY = cell.getY() + dir.y();
        if (isOutOfBounds(neighborX, neighborY)) {
            return Optional.empty();
        } else {
            return Optional.of(new Cell(neighborX, neighborY));
        }
    }

    /**
     * Returns a list of cells representing the neighbors of the given cell
     *
     * @param cell the cell of which we want to find the neighbors
     * @return a list of cells representing the neighbors of the given cell
     */

    public Set<Cell> neighborsOf(Cell cell) {
        Set<Cell> neighbors = new HashSet<>();

        for (Direction dir : Direction.values()) {
            Optional<Cell> possibleNeighbor = neighborOf(cell, dir);
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
        Map<Cell, Worker> workerCells = new HashMap<>();
        for (Worker w : workers) {
            workerCells.put(w.getPosition(), w);
        }
        return workerCells;
    }

    /**
     * Returns a map linking the towers (values) to the cells they occupy (keys).
     *
     * @return a map linking towers and the cells they occupy
     */

    public Map<Cell, Tower> getTowersPositions() {
        Map<Cell, Tower> towerCells = new HashMap<>();
        for (Tower t : towers) {
            towerCells.put(t.getPosition(), t);
        }
        return towerCells;
    }

    /**
     * Determines if the given cell contains a dome
     *
     * @param cell the cell that we want to check
     * @return <b>true</b> if the cell contains a dome , <b>false</b> otherwise
     */

    public boolean hasDomeAt(Cell cell) {
        return cellsWithDomes.contains(cell);
    }

    /**
     * Determines if the given cell contains a worker
     *
     * @param cell the cell that we want to check
     * @return <b>true</b> if the cell contains a worker , <b>false</b> otherwise
     */

    public boolean hasWorkerAt(Cell cell) {
        return getWorkersPositions().containsKey(cell);
    }

    /**
     * Returns the worker placed at the specified cell.
     *
     * @param cell the cell where the worker is standing
     * @return the worker placed on the given cell
     * @throws NullPointerException if the cell doesn't contain a worker
     */

    public Worker workerAt(Cell cell) throws NullPointerException {
        if (!hasWorkerAt(cell)) {
            throw new NullPointerException("The selected cell doesn't contain a worker.");
        }

        return getWorkersPositions().get(cell);
    }

    /**
     * Returns the height of the given cell
     *
     * @param cell the cell
     * @return the height
     */

    public int heightOf(Cell cell) {
        return !getTowersPositions().containsKey(cell) ? 0 : getTowersPositions().get(cell).getHeight();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Board other = (Board) obj;
        return workers.equals(other.workers) &&
                towers.equals(other.towers) &&
                cellsWithDomes.equals(other.cellsWithDomes);
    }
}
