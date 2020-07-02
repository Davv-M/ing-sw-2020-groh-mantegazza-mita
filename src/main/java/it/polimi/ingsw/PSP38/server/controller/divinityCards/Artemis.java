package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.controller.OptionalAction;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Concrete implementation of Artemis' power, extends <code>DivinityCard</code> abstract class.
 *
 * @author Maximilien Groh (10683107)
 */

public class Artemis extends DivinityCard implements OptionalAction {
    private static final List<WorkerAction> moveSequence = List.of(WorkerAction.MOVE, WorkerAction.OPTIONAL_ACTION, WorkerAction.BUILD);
    private Cell previousPosition = null;

    @Override
    public Board move(Worker worker, Cell destinationCell, Board currentBoard, boolean isSimulation) throws IllegalArgumentException {
        Board updatedBoard = super.move(worker, destinationCell, currentBoard, isSimulation);
        previousPosition = isSimulation ? previousPosition : worker.getPosition();

        return updatedBoard;
    }

    @Override
    public Board optionalAction(Worker worker, Cell destinationCell, Board currentBoard, boolean isSimulation) {
        if (destinationCell.equals(previousPosition)) {
            throw new IllegalArgumentException("You can't move back to your previous position");
        }

        return super.move(worker, destinationCell, currentBoard, isSimulation);
    }

    @Override
    public WorkerAction typeOfAction(WorkerAction action) {
        return action == WorkerAction.OPTIONAL_ACTION ? WorkerAction.MOVE : super.typeOfAction(action);
    }

    @Override
    public List<WorkerAction> getMoveSequence() {
        return moveSequence;
    }

    @Override
    public String toString() {
        return "Artemis";
    }
}
