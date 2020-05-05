package it.polimi.ingsw.PSP38.server.controller.divinityStrategies;

import it.polimi.ingsw.PSP38.server.controller.StrategyDivinityCard;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.List;
import java.util.Map;

/**
 * Concrete implementation of Apollo' power, extends <code>StrategyDivinityCard</code> interface.
 * @author Matteo Mita (10487862)
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
        //Removes all cells containing workers' player who call the method, domes or cells with tower height > cell.towerHeight + 1
        neighborCells.removeIf(c -> c.hasDome() || c.getTowerHeight() > worker.getPosition().getTowerHeight() + 1);
        neighborCells.removeIf(c -> workersPositions.containsKey(c) && workersPositions.get(c).getColor() == worker.getColor());
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

        Board boardUpdated;
        Worker challengerWorker = oldBoard.workerAt(destinationCell);
        Cell oldCell = worker.getPosition();
        boardUpdated = oldBoard.moveWorker(worker, destinationCell);
        if (challengerWorker != null) {
            boardUpdated = boardUpdated.moveWorker(challengerWorker, oldCell);
        }

        return boardUpdated;

    }

}
