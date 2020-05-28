package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;
import static it.polimi.ingsw.PSP38.server.utilities.ArgumentChecker.*;

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
    public void checkMove(Worker worker, Cell destinationCell, Board currentBoard) {
        checkNeighbor(worker, destinationCell, currentBoard);
        checkDome(destinationCell, currentBoard);
        checkHeight(worker, destinationCell, currentBoard);
        checkWorkerSameColor(worker, destinationCell, currentBoard);
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

        Board boardUpdated = currentBoard.withoutWorker(worker);
        if (currentBoard.hasWorkerAt(destinationCell)) {
            Worker opponent = currentBoard.getWorkersPositions().get(destinationCell);
            boardUpdated = boardUpdated.withoutWorker(opponent).
                    withWorker(opponent.withPosition(oldCell));
        }
        return boardUpdated.withWorker(worker.withPosition(destinationCell));
    }
}
