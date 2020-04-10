package it.polimi.ingsw.PSP38.controller;

import it.polimi.ingsw.PSP38.model.*;

import java.util.List;
import java.util.Map;

public interface StrategyDivinityCard {
    /**
     * method that return a list of the possible cell where the given worker can move on
     * can move on
     *
     * @param worker       the worker that want moves
     * @param currentBoard the current board of the game
     */
    default List<Cell> preMove(Worker worker, Board currentBoard) {
        List<Cell> neighborCells = currentBoard.neighborsCells(worker.getPosition());
        Map<Cell, Worker> workersPositions = currentBoard.getWorkersPositions();
        neighborCells.removeIf(c -> workersPositions.containsKey(c) || c.hasDome() || c.getTowerHeight() > worker.getPosition().getTowerHeight() + 1); //Removes cell that is already occupied or there is a complete tower or a tower whose level is more than player+1
        return neighborCells;
    }

    /**
     * method that move the worker in the given cell and create the new board updated
     *
     * @param worker          the worker that have to change cell
     * @param destinationCell the cell where the worker move on
     * @param oldBoard        the current game's board
     */

    default Board move(Worker worker, Cell destinationCell, Board oldBoard) {
        return oldBoard.withWorker(worker, destinationCell);
    }

    /**
     * method that return a list of the possible cell where the given worker can build on
     *
     * @param worker       the worker that want moves
     * @param currentBoard the current board of the game
     */
    default List<Cell> preBuild(Worker worker, Board currentBoard) {
        List<Cell> cellsCanBuild = currentBoard.neighborsCells(worker.getPosition());
        Map<Cell, Worker> workersPositions = currentBoard.getWorkersPositions();
        cellsCanBuild.removeIf(c -> workersPositions.containsKey(c) || c.hasDome()); //Removes the cells where there is a worker or the cell that just have a dome
        return cellsCanBuild;
    }

    /**
     * method that add the tower's level at the given cell and update the board
     *
     * @param buildingCell the cell where the worker's build on a new level
     * @param oldBoard     the current game's board
     */
    default Board build(Cell buildingCell, Board oldBoard) {
        Cell modifiedCell = buildingCell.getTowerHeight() == 3 ? buildingCell.withDome() : buildingCell.withTowerHeight(buildingCell.getTowerHeight() + 1);
        return oldBoard.withCell(modifiedCell);
    }
}