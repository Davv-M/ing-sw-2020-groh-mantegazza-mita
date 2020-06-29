package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.controller.OptionalAction;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Concrete implementation of Prometheus' power, extends <code>StrategyDivinityCard</code> interface.
 *
 * @author Maximilien Groh (10683107)
 */

public class Prometheus extends DivinityCard implements OptionalAction {
    private static final List<WorkerAction> moveSequence = Arrays.asList(WorkerAction.OPTIONAL_ACTION, WorkerAction.MOVE, WorkerAction.BUILD);
    private boolean hasBuiltFirstMove = false;

    @Override
    public Board move(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        checkMove(worker, destinationCell, currentBoard);
        checkSameHeight(worker, destinationCell, currentBoard);
        Board updatedBoard = currentBoard.withoutWorker(worker).withWorker(worker.withPosition(destinationCell));
        hasBuiltFirstMove = false;

        return updatedBoard;
    }

    private void checkSameHeight(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException{
        if(hasBuiltFirstMove && currentBoard.heightOf(destinationCell) > currentBoard.heightOf(worker.getPosition())){
            throw new IllegalArgumentException("You can't jump on that tower because you've built on your first move.");
        }
    }

    @Override
    public Board optionalAction(Worker worker, Cell destinationCell, Board currentBoard) {
        Board updatedBoard = super.build(worker, destinationCell, currentBoard);
        hasBuiltFirstMove = true;
        return updatedBoard;
    }

    @Override
    public WorkerAction typeOfAction(WorkerAction action) {
        return action == WorkerAction.OPTIONAL_ACTION ? WorkerAction.BUILD : super.typeOfAction(action);
    }

    @Override
    public List<WorkerAction> getMoveSequence() {
        return moveSequence;
    }
}
