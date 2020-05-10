package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.List;

public class Demeter extends DivinityCard {
    private static final List<WorkerAction> moveSequence = List.of(WorkerAction.MOVE, WorkerAction.BUILD, WorkerAction.SPECIAL_BUILD);
    private Cell previousBuild = null;

    @Override
    public Board build(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        Board updatedBoard = super.build(worker, destinationCell, currentBoard);
        previousBuild = destinationCell;

        return updatedBoard;
    }

    @Override
    public Board specialBuild(Worker worker, Cell destinationCell, Board currentBoard) {
        if(destinationCell.equals(previousBuild)){
            throw new IllegalArgumentException("You can't build twice on the same cell");
        }

        return super.build(worker, destinationCell, currentBoard);
    }

    @Override
    public List<WorkerAction> getMoveSequence() {
        return moveSequence;
    }
}
