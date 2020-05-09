package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Concrete implementation of Apollo' power, extends <code>StrategyDivinityCard</code> interface.
 *
 * @author Matteo Mita (10487862)
 */

public class Apollo extends DivinityCard {

    /**
     * Returns a list of cells where the given worker can move
     *
     * @param worker       the worker that has to be moved
     * @param currentBoard the current board of the game
     */
    @Override
    public Set<Cell> preMove(Worker worker, Board currentBoard) {
        Set<Cell> neighborCells = currentBoard.neighborsOf(worker.getPosition());
        Map<Cell, Worker> workersPositions = currentBoard.getWorkersPositions();
        //Removes all cells containing workers' player who call the method, domes or cells with tower height > cell.towerHeight + 1
        neighborCells.removeIf(c -> currentBoard.hasDomeAt(c) || currentBoard.heightOf(c) > currentBoard.heightOf(worker.getPosition()) + 1);
        neighborCells.removeIf(c -> currentBoard.hasWorkerAt(c) && workersPositions.get(c).getColor() == worker.getColor());
        return neighborCells;
    }


    /**
     * Moves the given worker on the given cell and returns the updated board
     *
     * @param worker          the worker that has to be moved
     * @param destinationCell the cell where the worker has to be moved
     * @param currentBoard    the current board of the game
     * @return the updated board
     */
    @Override
    public Board move(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException{
        Cell oldCell = worker.getPosition();

        if (!preMove(worker, currentBoard).contains(destinationCell)) {
            throw new IllegalArgumentException("you can't move on this cell.");
        }

        Board boardUpdated = currentBoard;
        if (currentBoard.hasWorkerAt(destinationCell)) {
            Worker challengerWorker = currentBoard.getWorkersPositions().get(destinationCell);
            boardUpdated = currentBoard.withoutWorker(challengerWorker).
                    withWorker(new Worker(challengerWorker.getColor(), oldCell));
        }
        return boardUpdated.withWorker(new Worker(worker.getColor(), destinationCell));
    }

}
