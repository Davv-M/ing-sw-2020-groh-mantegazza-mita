package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.controller.OptionalAbility;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.List;

public class Atlas extends DivinityCard implements OptionalAbility {
    private static final List<WorkerAction> moveSequence = List.of(WorkerAction.MOVE,
            WorkerAction.OPTIONAL_ABILITY);

    @Override
    public Board optionalAbility(boolean buildDome, Worker worker, Cell destinationCell, Board currentBoard) {
        if(buildDome){
            if (!preBuild(worker, currentBoard).contains(destinationCell)) {
                throw new IllegalArgumentException("you can't build on that cell.");
            }

            return currentBoard.withDome(destinationCell);
        } else {
            return super.build(worker, destinationCell, currentBoard);
        }
    }

    @Override
    public List<WorkerAction> getMoveSequence() {
        return moveSequence;
    }
}
