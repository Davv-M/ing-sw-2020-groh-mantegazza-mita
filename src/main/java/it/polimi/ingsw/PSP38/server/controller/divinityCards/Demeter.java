package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.controller.OptionalAction;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.List;

/**
 * Concrete implementation of Demeter's power, extends <code>DivinityCard</code> abstract class.
 *
 * @author Maximilien Groh (10683107)
 */

public class Demeter extends DivinityCard implements OptionalAction {
    private static final List<WorkerAction> moveSequence = List.of(WorkerAction.MOVE, WorkerAction.BUILD, WorkerAction.OPTIONAL_ACTION);
    private Cell previousBuild = null;

    @Override
    public Board build(Worker worker, Cell destinationCell, Board currentBoard, boolean isSimulation) throws IllegalArgumentException {
        Board updatedBoard = super.build(worker, destinationCell, currentBoard, isSimulation);
        previousBuild = isSimulation ? previousBuild : destinationCell;

        return updatedBoard;
    }

    @Override
    public Board optionalAction(Worker worker, Cell destinationCell, Board currentBoard, boolean isSimulation) {
        if (destinationCell.equals(previousBuild)) {
            throw new IllegalArgumentException("You can't build twice on the same cell");
        }

        return super.build(worker, destinationCell, currentBoard, isSimulation);
    }

    @Override
    public WorkerAction typeOfAction(WorkerAction action) {
        return action == WorkerAction.OPTIONAL_ACTION ? WorkerAction.BUILD : super.typeOfAction(action);
    }

    @Override
    public List<WorkerAction> getMoveSequence() {
        return moveSequence;
    }

    @Override
    public String toString() {
        return "Demeter";
    }
}
