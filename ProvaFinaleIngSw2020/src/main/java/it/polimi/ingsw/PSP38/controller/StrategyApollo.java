package it.polimi.ingsw.PSP38.controller;

import it.polimi.ingsw.PSP38.model.Board;
import it.polimi.ingsw.PSP38.model.Cell;
import it.polimi.ingsw.PSP38.model.Worker;

import java.util.List;
import java.util.Map;

/**
 * Class that modify a normal round with the Apollo's power
 */

public class StrategyApollo implements StrategyDivinityCard {

    /**
     * Returns a list of cells where the given worker can move
     *
     * @param worker       the worker that has to be moved
     * @param currentBoard the current board of the game
     */

    @Override
    public List<Cell> preMove(Worker worker, Board currentBoard) {
        List<Cell> neighborCells = currentBoard.neighborsCells(worker.getPosition());
        Map<Cell, Worker> workersPositions = currentBoard.getWorkersPositions();
        for (Cell c : neighborCells) {
            if (c.hasDome() || c.getTowerHeight() > worker.getPosition().getTowerHeight() + 1) neighborCells.remove(c);
            if (workersPositions.containsKey(c)) {
                if (workersPositions.get(c).getColor() == worker.getColor()) neighborCells.remove(c);
            }
        }


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
    @Override
    public Board move(Worker worker, Cell destinationCell, Board oldBoard) {

        Board boardUpdated = oldBoard;
        Worker challengerWorker = oldBoard.workerAt(destinationCell);
        Cell oldCell = worker.getPosition();
        boardUpdated = oldBoard.moveWorker(worker, destinationCell);
        boardUpdated = boardUpdated.moveWorker(challengerWorker, oldCell);
        return boardUpdated;

    }

}
