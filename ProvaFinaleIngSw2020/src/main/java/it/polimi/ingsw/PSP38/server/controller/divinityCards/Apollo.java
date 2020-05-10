package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

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
        neighborCells.removeIf(c -> currentBoard.heightOf(c) > currentBoard.heightOf(worker.getPosition()) + 1);
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
            throw new IllegalArgumentException("you can't move on that cell.");
        }

        Board boardUpdated = currentBoard.withoutWorker(worker);
        if (currentBoard.hasWorkerAt(destinationCell)) {
            Worker opponent = currentBoard.getWorkersPositions().get(destinationCell);
            boardUpdated = boardUpdated.withoutWorker(opponent).
                    withWorker(opponent.withPosition(oldCell));
        }
        return boardUpdated.withWorker(worker.withPosition(destinationCell));
    }

}