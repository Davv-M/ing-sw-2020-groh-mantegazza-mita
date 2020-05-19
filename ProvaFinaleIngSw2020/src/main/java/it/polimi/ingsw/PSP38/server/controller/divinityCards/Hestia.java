package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.controller.OptionalAction;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.List;

public class Hestia extends DivinityCard implements OptionalAction {
    private static final List<WorkerAction> moveSequence = List.of(WorkerAction.MOVE, WorkerAction.BUILD, WorkerAction.OPTIONAL_ACTION);
    @Override
    public Board optionalAction(Worker worker, Cell destinationCell, Board currentBoard) {
        if(destinationCell.getX() == Board.COLUMNS - 1 || destinationCell.getY() == Board.ROWS - 1){
            throw new IllegalArgumentException("You can't build on a perimeter cell");
        }

        return super.build(worker, destinationCell, currentBoard);
    }

    @Override
    public List<WorkerAction> getMoveSequence() {
        return moveSequence;
    }
}
