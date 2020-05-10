package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.Arrays;
import java.util.List;

public class Hephaestus extends DivinityCard {
    private static final List<WorkerAction> moveSequence = Arrays.asList(WorkerAction.MOVE, WorkerAction.BUILD, WorkerAction.SPECIAL_BUILD);
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
            if(currentBoard.heightOf(destinationCell) == 3){
                throw new IllegalArgumentException("Your second build can't be a dome");
            }
        }

        return super.build(worker, destinationCell, currentBoard);
    }

    @Override
    public List<WorkerAction> getMoveSequence() {
        return moveSequence;
    }
}
