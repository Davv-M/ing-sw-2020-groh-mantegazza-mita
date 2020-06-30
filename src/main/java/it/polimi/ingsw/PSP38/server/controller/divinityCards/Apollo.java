package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;
import static it.polimi.ingsw.PSP38.common.utilities.ArgumentChecker.*;

/**
 * Concrete implementation of Apollo' power, extends <code>DivinityCard</code> abstract class.
 *
 * @author Matteo Mita (10487862)
 */

public class Apollo extends DivinityCard {

    @Override
    public void checkMove(Worker worker, Cell destinationCell, Board currentBoard) {
        checkNeighbor(worker, destinationCell, currentBoard);
        checkDome(destinationCell, currentBoard);
        checkHeight(worker, destinationCell, currentBoard);
        checkWorkerSameColor(worker, destinationCell, currentBoard);
    }

    @Override
    public Board move(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException{
        checkMove(worker, destinationCell, currentBoard);
        Cell oldCell = worker.getPosition();

        Board boardUpdated = currentBoard.withoutWorker(worker);
        if (currentBoard.hasWorkerAt(destinationCell)) {
            Worker opponent = currentBoard.getWorkersPositions().get(destinationCell);
            boardUpdated = boardUpdated.withoutWorker(opponent).
                    withWorker(opponent.withPosition(oldCell));
        }
        return boardUpdated.withWorker(worker.withPosition(destinationCell));
    }

    @Override
    public String toString() {
        return "Apollo";
    }
}
