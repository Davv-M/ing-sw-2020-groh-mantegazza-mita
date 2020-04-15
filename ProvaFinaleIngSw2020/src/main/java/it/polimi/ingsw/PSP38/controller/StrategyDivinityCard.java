package it.polimi.ingsw.PSP38.controller;

import it.polimi.ingsw.PSP38.model.*;

import java.util.List;
import java.util.Map;

/**
 * Interface representing a divinity card of the game Santorini.
 *
 * @author Maximilien Groh (10683107), Davide Mantegazza (10568661), Matteo Mita (10487862)
 */

public interface StrategyDivinityCard {
    /**
     * Returns a list of cells where the given worker can move
     *
     * @param worker       the worker that has to be moved
     * @param currentBoard the current board of the game
     */
    default List<Cell> preMove(Worker worker, Board currentBoard) {
        List<Cell> neighborCells = currentBoard.neighborsCells(worker.getPosition());
        Map<Cell, Worker> workersPositions = currentBoard.getWorkersPositions();

        //Removes all cells containing workers or domes or cells with tower height > cell.towerHeight + 1
        neighborCells.removeIf(c -> workersPositions.containsKey(c) || c.hasDome() || c.getTowerHeight() >
                worker.getPosition().getTowerHeight() + 1);

        return neighborCells;
    }

    /**
     * Moves the given worker on the given cell and returns the updated board
     *
     * @param worker          the worker that has to be moved
     * @param destinationCell the cell where the worker has to be moved
     * @param oldBoard        the current board of the game
     * @return the updated board
     */

    default Board move(Worker worker, Cell destinationCell, Board oldBoard) {
        return oldBoard.withWorker(worker, destinationCell);
    }

    /**
     * Returns a list of possible cells where the given worker can build
     *
     * @param worker       the worker that has to build
     * @param currentBoard the current board of the game
     * @return a list of possible cells where the given worker can build
     */
    default List<Cell> preBuild(Worker worker, Board currentBoard) {
        List<Cell> cellsCanBuild = currentBoard.neighborsCells(worker.getPosition());
        Map<Cell, Worker> workersPositions = currentBoard.getWorkersPositions();

        //Removes the cells containing workers or domes
        cellsCanBuild.removeIf(c -> workersPositions.containsKey(c) || c.hasDome());

        return cellsCanBuild;
    }

    /**
     * Adds a tower level or a dome to the given cell, depending on the current tower's height and returns the updated board
     *
     * @param cell     the cell on which to build
     * @param oldBoard the current board of the game
     * @return the updated board with the updated cell's tower's height
     */
    default Board build(Cell cell, Board oldBoard) {
        int newTowerHeight = cell.getTowerHeight() + 1;
        Cell modifiedCell = cell.getTowerHeight() == Cell.MAX_TOWER_HEIGHT ?
                cell.withDome() : cell.withTowerHeight(newTowerHeight);
        return oldBoard.withCell(modifiedCell);
    }
}