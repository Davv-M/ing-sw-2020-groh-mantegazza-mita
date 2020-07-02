package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.controller.OptionalAction;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Direction;
import it.polimi.ingsw.PSP38.server.model.Worker;

import static it.polimi.ingsw.PSP38.common.utilities.ArgumentChecker.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Concrete implementation of Charon's power, extends <code>DivinityCard</code> abstract class.
 *
 * @author Maximilien Groh (10683107)
 */

public class Charon extends DivinityCard implements OptionalAction {
    private static final List<WorkerAction> moveSequence = Arrays.asList(WorkerAction.OPTIONAL_ACTION, WorkerAction.MOVE, WorkerAction.BUILD);

    @Override
    public Board optionalAction(Worker worker, Cell destinationCell, Board currentBoard, boolean isSimulation) {
        checkNeighbor(worker, destinationCell, currentBoard);
        checkNoWorker(destinationCell, currentBoard);
        checkWorkerSameColor(worker, destinationCell, currentBoard);
        int vectorX = destinationCell.getX() - worker.getPosition().getX();
        int vectorY = destinationCell.getY() - worker.getPosition().getY();

        Direction dir = Direction.coordinatesToDirection(vectorX, vectorY);
        Optional<Cell> possibleNeighbor = currentBoard.neighborOf(worker.getPosition(), dir.opposite());
        if (possibleNeighbor.isPresent()
                && !currentBoard.hasDomeAt(possibleNeighbor.get())
                && !currentBoard.hasWorkerAt(possibleNeighbor.get())) {

            Worker opponent = currentBoard.getWorkersPositions().get(destinationCell);

            return currentBoard.withoutWorker(opponent).withWorker(opponent.withPosition(possibleNeighbor.get()));
        } else {
            throw new IllegalArgumentException("the selected worker can't be moved");
        }
    }

    @Override
    public List<WorkerAction> getMoveSequence() {
        return moveSequence;
    }

    @Override
    public String toString() {
        return "Charon";
    }
}
