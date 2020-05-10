package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.Arrays;
import java.util.List;

public class Prometheus extends DivinityCard {
    private static final List<WorkerAction> moveSequence = Arrays.asList(WorkerAction.SPECIAL_BUILD, WorkerAction.MOVE, WorkerAction.BUILD);
    private boolean hasBuiltFirstMove = false;

    @Override
    public Board move(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        if (!preMove(worker, currentBoard).contains(destinationCell)) {
            throw new IllegalArgumentException("You can't move on that cell.");
        }
        Board updatedBoard = currentBoard.withoutWorker(worker).withWorker(worker.withPosition(destinationCell));
        hasBuiltFirstMove = false;

        return updatedBoard;
    }

    @Override
    public Board specialBuild(Worker worker, Cell destinationCell, Board currentBoard) {
        Board updatedBoard = super.build(worker, destinationCell, currentBoard);
        hasBuiltFirstMove = true;
        return updatedBoard;
    }

    @Override
    public List<WorkerAction> getMoveSequence() {
        return moveSequence;
    }
}
