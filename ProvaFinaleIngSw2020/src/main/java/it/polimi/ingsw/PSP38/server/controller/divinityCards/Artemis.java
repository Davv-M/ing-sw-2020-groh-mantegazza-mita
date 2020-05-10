package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.Arrays;
import java.util.List;

/**
 * Concrete implementation of Artemis' power, extends <code>StrategyDivinityCard</code> interface.
 */
public class Artemis extends DivinityCard {
    private final List<WorkerAction> moveSequence = List.of(WorkerAction.MOVE, WorkerAction.SPECIAL_MOVE, WorkerAction.BUILD);
    private Cell previousPosition = null;

    @Override
    public Board move(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        return moveAndUpdatePreviousPosition(worker, destinationCell, currentBoard);
    }

    @Override
    public Board specialMove(Worker worker, Cell destinationCell, Board currentBoard) {
        if(destinationCell.equals(previousPosition)){
            throw new IllegalArgumentException("You can't move back to your previous position");
        }

        return moveAndUpdatePreviousPosition(worker, destinationCell, currentBoard);
    }

    @Override
    public List<WorkerAction> getMoveSequence() {
        return moveSequence;
    }

    private Board moveAndUpdatePreviousPosition(Worker worker, Cell destinationCell, Board currentBoard){
        Board updatedBoard = super.move(worker, destinationCell, currentBoard);
        previousPosition = worker.getPosition();

        return updatedBoard;
    }
}
