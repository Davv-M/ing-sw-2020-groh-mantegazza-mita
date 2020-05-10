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

public class Prometheus extends DivinityCard implements OptionalAction {
    private static final List<WorkerAction> moveSequence = Arrays.asList(WorkerAction.OPTIONAL_ACTION, WorkerAction.MOVE, WorkerAction.BUILD);
    private boolean hasBuiltFirstMove = false;

    @Override
    public Set<Cell> preMove(Worker worker, Board currentBoard) {
        Set<Cell> neighborCells = currentBoard.neighborsOf(worker.getPosition());
        if(hasBuiltFirstMove){
            neighborCells.removeIf(c -> currentBoard.heightOf(c) > currentBoard.heightOf(worker.getPosition()));
        }

        return neighborCells;
    }

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
    public Board optionalAction(Worker worker, Cell destinationCell, Board currentBoard) {
        Board updatedBoard = super.build(worker, destinationCell, currentBoard);
        hasBuiltFirstMove = true;
        return updatedBoard;
    }

    @Override
    public List<WorkerAction> getMoveSequence() {
        return moveSequence;
    }
}
