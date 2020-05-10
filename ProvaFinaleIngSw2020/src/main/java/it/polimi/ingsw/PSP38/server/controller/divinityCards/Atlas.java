package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.List;

public class Atlas extends DivinityCard {
    private static final List<WorkerAction> moveSequence = List.of(WorkerAction.MOVE,
            WorkerAction.SPECIAL_BUILD, WorkerAction.BUILD);
    private boolean buildDome = false;

    @Override
    public Board build(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        if(buildDome){
            if (!preBuild(worker, currentBoard).contains(destinationCell)) {
                throw new IllegalArgumentException("you can't build on that cell.");
            }
            Board updatedBoard = currentBoard.withDome(destinationCell);
            buildDome = false;

            return updatedBoard;
        } else {
            return super.build(worker, destinationCell, currentBoard);
        }
    }

    @Override
    public Board specialBuild(Worker worker, Cell destinationCell, Board currentBoard) {
        buildDome = true;
        return currentBoard;
    }

    @Override
    public List<WorkerAction> getMoveSequence() {
        return moveSequence;
    }
}
